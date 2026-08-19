import React, { useEffect, useState } from "react";
import { Dumbbell, PlayCircle } from "lucide-react";
import AppShell from "../components/app/AppShell";
import GlassCard from "../components/landing/GlassCard";

export default function Workouts() {
  const [days, setDays] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadWorkout();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const loadWorkout = async () => {
    try {
      const user = JSON.parse(localStorage.getItem("user"));

      console.log("USER =", user);

      const response = await fetch(
        `http://localhost:8082/api/workouts/generate/${user.id}`
      );

      const data = await response.json();

      console.log(data.workoutPlan);

      const workoutJson = JSON.parse(data.workoutPlan);

      console.log("WORKOUT JSON =", workoutJson);

      setDays(workoutJson.days || []);
    } catch (error) {
      console.error("WORKOUT ERROR =", error);
    } finally {
      setLoading(false);
    }
  };

  const openVideo = (exercise) => {
    const searchUrl =
      "https://www.youtube.com/results?search_query=" +
      encodeURIComponent(exercise);

    window.open(searchUrl, "_blank");
  };

  if (loading) {
    return (
      <AppShell>
        <div className="min-h-[80vh] flex items-center justify-center text-white/60 text-sm">
          Generating AI Workout...
        </div>
      </AppShell>
    );
  }

  console.log("DAYS =", days);

  return (
    <AppShell>
      <div className="px-5 sm:px-8 py-8 max-w-4xl mx-auto">
        <h1 className="text-2xl font-semibold flex items-center gap-2 mb-8">
          <Dumbbell size={22} className="text-[#4DFFB2]" aria-hidden="true" />
          AI Workout Plan
        </h1>

        <div className="flex flex-col gap-5">
          {days.map((day, index) => (
            <GlassCard key={index} strong className="p-6" hover={false}>
              <h2 className="text-lg font-semibold text-[#4DFFB2]">{day.day}</h2>
              <h3 className="text-sm text-white/55 mb-4">{day.focus}</h3>

              {day.exercises?.length > 0 ? (
                <div className="space-y-3">
                  {day.exercises.map((exercise, i) => (
                    <div
                      key={i}
                      className="bg-white/5 border border-white/10 rounded-xl p-4 flex items-center justify-between gap-4"
                    >
                      <div>
                        <h4 className="font-medium">{exercise.name}</h4>
                        <p className="text-sm text-white/55 mt-0.5">
                          Sets: {exercise.sets} | Reps: {exercise.reps}
                        </p>
                      </div>
                      <button
                        onClick={() => openVideo(exercise.name)}
                        className="shrink-0 inline-flex items-center gap-1.5 bg-[#4DFFB2] text-[#03110B] text-sm font-semibold px-4 py-2 rounded-lg hover:shadow-[0_0_25px_rgba(77,255,178,0.4)] transition-shadow"
                      >
                        <PlayCircle size={15} /> Watch Demo
                      </button>
                    </div>
                  ))}
                </div>
              ) : (
                <p className="text-white/45 text-sm">Rest Day 😴</p>
              )}
            </GlassCard>
          ))}
        </div>
      </div>
    </AppShell>
  );
}
