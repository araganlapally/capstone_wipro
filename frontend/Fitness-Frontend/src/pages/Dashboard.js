import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Flame, Scale, Footprints, Gauge, PlayCircle, Dumbbell } from "lucide-react";
import AppShell from "../components/app/AppShell";
import GlassCard from "../components/landing/GlassCard";
import GlassButton from "../components/landing/GlassButton";
import WorkoutImageCarousel from "../components/app/WorkoutImageCarousel";

function getGreeting() {
  const hour = new Date().getHours();
  if (hour < 12) return "Good Morning";
  if (hour < 17) return "Good Afternoon";
  return "Good Evening";
}

const WEEKDAYS = [
  "Sunday",
  "Monday",
  "Tuesday",
  "Wednesday",
  "Thursday",
  "Friday",
  "Saturday",
];

// Finds the entry in the AI-generated workout plan that corresponds to
// today's actual weekday, so the Dashboard preview stays in sync with
// the real plan shown on the Workouts page (same data source).
function findTodaysWorkout(days) {
  if (!days || days.length === 0) return null;

  const todayName = WEEKDAYS[new Date().getDay()];

  const byName = days.find(
    (d) => d.day && d.day.toLowerCase().includes(todayName.toLowerCase())
  );
  if (byName) return byName;

  if (days.length === 7) {
    const mondayFirstIndex = (new Date().getDay() + 6) % 7;
    return days[mondayFirstIndex];
  }

  return days[0];
}

export default function Dashboard() {
  const [user, setUser] = useState(null);
  const [greeting, setGreeting] = useState(getGreeting());

  const [todaysWorkout, setTodaysWorkout] = useState(null);
  const [workoutLoading, setWorkoutLoading] = useState(true);
  const [workoutError, setWorkoutError] = useState(false);

  const navigate = useNavigate();

  useEffect(() => {
    try {
      const stored = localStorage.getItem("user");
      if (stored && stored !== "undefined") {
        setUser(JSON.parse(stored));
      }
    } catch {
      setUser(null);
    }
  }, []);

  // Keep the greeting accurate even if the dashboard is left open across
  // a time-of-day boundary (e.g. noon -> afternoon) without a refresh.
  useEffect(() => {
    const interval = setInterval(() => setGreeting(getGreeting()), 60 * 1000);
    return () => clearInterval(interval);
  }, []);

  // Pull the same AI-generated workout plan used on the Workouts page,
  // so "Today's Workout" here actually reflects today's real session
  // instead of a static placeholder.
  useEffect(() => {
    const loadTodaysWorkout = async () => {
      try {
        const storedUser = JSON.parse(localStorage.getItem("user"));
        if (!storedUser?.id) {
          setWorkoutError(true);
          return;
        }

        const response = await fetch(
          `http://localhost:8082/api/workouts/generate/${storedUser.id}`
        );
        const data = await response.json();
        const workoutJson = JSON.parse(data.workoutPlan);
        const days = workoutJson.days || [];

        setTodaysWorkout(findTodaysWorkout(days));
      } catch (err) {
        console.error("DASHBOARD WORKOUT ERROR =", err);
        setWorkoutError(true);
      } finally {
        setWorkoutLoading(false);
      }
    };

    loadTodaysWorkout();
  }, []);

  const METRICS = [
    { icon: Flame, label: "Calories Burned", value: "568", unit: "kcal", trend: "↑ 12% vs yesterday" },
    { icon: Scale, label: "Weight Progress", value: "72.4", unit: "kg", trend: "↓ 1.3 kg vs last week" },
    { icon: Footprints, label: "Daily Steps", value: "8,432", unit: "", trend: "↑ 15% vs yesterday" },
    { icon: Gauge, label: "AI Fitness Score", value: "86", unit: "/100", trend: "Excellent" },
  ];

  return (
    <AppShell>
      <div className="px-5 sm:px-8 py-8 max-w-7xl mx-auto">
        {/* HEADER */}
        <div className="flex items-center justify-between mb-8">
          <div>
            <h1 className="text-2xl font-semibold">
              {greeting}, {user?.fullName || user?.name || user?.email || "User"} 👋
            </h1>
            <p className="text-white/50 text-sm mt-1">
              You're crushing your goals. Keep it up!
            </p>
          </div>
        </div>

        {/* METRIC CARDS */}
        <div className="grid grid-cols-2 lg:grid-cols-4 gap-4 mb-6">
          {METRICS.map((m) => {
            const Icon = m.icon;
            return (
              <GlassCard key={m.label} className="p-5" hover={false}>
                <div className="flex items-center gap-2 text-white/45 text-xs mb-3">
                  <Icon size={14} aria-hidden="true" />
                  {m.label}
                </div>
                <p className="text-2xl font-semibold">
                  {m.value}
                  <span className="text-xs text-white/45 ml-1">{m.unit}</span>
                </p>
                <p className="text-xs text-[#4DFFB2] mt-2">{m.trend}</p>
              </GlassCard>
            );
          })}
        </div>

        {/* MAIN CONTENT */}
        <div className="grid lg:grid-cols-3 gap-5 mb-5">
          {/* TODAY'S WORKOUT — image carousel of today's real exercises */}
          <GlassCard strong glow className="lg:col-span-2 p-6 flex flex-col" hover={false}>
            <div className="flex items-center gap-2 mb-1">
              <Dumbbell size={16} className="text-[#4DFFB2]" aria-hidden="true" />
              <h3 className="font-semibold">Today's Workout</h3>
            </div>

            {workoutLoading ? (
              <p className="text-white/45 text-sm flex-1 mt-3">
                Loading today's plan...
              </p>
            ) : workoutError || !todaysWorkout ? (
              <>
                <p className="text-white/45 text-sm flex-1 mt-3">
                  No workout plan yet — generate one to see today's session
                  here.
                </p>
                <GlassButton
                  variant="primary"
                  className="w-full sm:w-auto mt-5"
                  onClick={() => navigate("/workouts")}
                >
                  Create My Workout
                </GlassButton>
              </>
            ) : (
              <>
                <p className="text-white/50 text-sm mb-4">
                  {todaysWorkout.day}
                  {todaysWorkout.focus ? ` — ${todaysWorkout.focus}` : ""}
                </p>

                {todaysWorkout.exercises?.length > 0 ? (
                  <WorkoutImageCarousel exercises={todaysWorkout.exercises} />
                ) : (
                  <p className="text-white/45 text-sm flex-1">Rest Day 😴</p>
                )}

                <GlassButton
                  variant="primary"
                  className="w-full sm:w-auto mt-5"
                  onClick={() => navigate("/workouts")}
                >
                  <PlayCircle size={16} /> Start Workout
                </GlassButton>
              </>
            )}
          </GlassCard>

          {/* NUTRITION */}
          <GlassCard className="p-6" hover={false}>
            <h3 className="font-semibold mb-4">Nutrition Today</h3>
            <div className="w-36 h-36 rounded-full border-[14px] border-[#4DFFB2] flex items-center justify-center mx-auto mb-5 text-lg font-semibold">
              1,842
            </div>
            <div className="space-y-1.5 text-sm text-white/75">
              <p className="flex justify-between">Protein <span>128g / 150g</span></p>
              <p className="flex justify-between">Carbs <span>185g / 250g</span></p>
              <p className="flex justify-between">Fat <span>56g / 70g</span></p>
            </div>
          </GlassCard>
        </div>

        {/* BOTTOM ROW */}
        <div className="grid sm:grid-cols-3 gap-5">
          <GlassCard className="p-5" hover={false}>
            <h4 className="text-sm text-white/60 mb-2">Weight Trend</h4>
            <p className="text-xl font-semibold">72.4 kg</p>
            <p className="text-xs text-[#4DFFB2] mt-1">↓ 1.3 kg vs last week</p>
          </GlassCard>
          <GlassCard className="p-5" hover={false}>
            <h4 className="text-sm text-white/60 mb-2">Weekly Calories</h4>
            <p className="text-xl font-semibold">Avg. 2,150 kcal</p>
            <p className="text-xs text-white/40 mt-1">Mon Tue Wed Thu Fri Sat Sun</p>
          </GlassCard>
          <GlassCard className="p-5" hover={false}>
            <h4 className="text-sm text-white/60 mb-2">Muscle Recovery</h4>
            <p className="text-xl font-semibold">78%</p>
            <p className="text-xs text-[#4DFFB2] mt-1">Good Recovery</p>
          </GlassCard>
        </div>
      </div>
    </AppShell>
  );
}
