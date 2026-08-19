import React from "react";
import { useNavigate } from "react-router-dom";
import { motion } from "framer-motion";
import { Dumbbell, Target, Gauge, Clock, Layers } from "lucide-react";
import GlassCard from "./GlassCard";
import GlassButton from "./GlassButton";

const EXERCISES = [
  { name: "Bench Press", sets: "4 × 10", extra: "70 kg" },
  { name: "Incline Dumbbell Press", sets: "3 × 10", extra: "24 kg" },
  { name: "Cable Fly", sets: "3 × 12", extra: "" },
  { name: "Triceps Pushdown", sets: "3 × 12", extra: "" },
];

const CONTEXT = [
  { icon: Target, label: "Goal", value: "Fat Loss" },
  { icon: Gauge, label: "Experience", value: "Intermediate" },
  { icon: Clock, label: "Session", value: "52 min" },
  { icon: Layers, label: "Equipment", value: "Full Gym" },
];

export default function WorkoutShowcase() {
  const navigate = useNavigate();

  return (
    <section id="workouts" className="relative py-24 px-6 md:px-10 max-w-6xl mx-auto">
      <motion.div
        initial={{ opacity: 0, y: 24 }}
        whileInView={{ opacity: 1, y: 0 }}
        viewport={{ once: true, margin: "-80px" }}
        transition={{ duration: 0.6 }}
        className="text-center mb-14"
      >
        <h2 className="text-3xl sm:text-4xl font-semibold tracking-tight">
          Your next workout <span className="text-gradient-mint">isn't random.</span>
        </h2>
      </motion.div>

      <div className="grid lg:grid-cols-3 gap-6 items-stretch">
        <GlassCard strong glow className="lg:col-span-2 p-6 sm:p-7" hover={false}>
          <div className="flex items-center justify-between mb-5">
            <div>
              <p className="text-xs uppercase tracking-wider text-white/45 mb-1">Today</p>
              <h3 className="text-xl font-semibold flex items-center gap-2">
                <Dumbbell size={18} className="text-[#4DFFB2]" /> Chest + Triceps
              </h3>
            </div>
          </div>

          <div className="space-y-2.5">
            {EXERCISES.map((ex) => (
              <div
                key={ex.name}
                className="flex items-center justify-between rounded-xl bg-white/5 border border-white/10 px-4 py-3"
              >
                <span className="text-sm text-white/85">{ex.name}</span>
                <span className="text-sm text-white/50">
                  {ex.sets}
                  {ex.extra && <span className="ml-2 text-[#4DFFB2]">{ex.extra}</span>}
                </span>
              </div>
            ))}
          </div>

          <GlassButton
            variant="primary"
            className="w-full sm:w-auto mt-6"
            onClick={() => navigate("/workouts")}
          >
            Create My Workout
          </GlassButton>
        </GlassCard>

        <div className="grid grid-cols-2 gap-4">
          {CONTEXT.map((c) => {
            const Icon = c.icon;
            return (
              <GlassCard key={c.label} className="p-4 flex flex-col gap-1.5" hover={false}>
                <Icon size={14} className="text-white/45" aria-hidden="true" />
                <span className="text-xs text-white/45">{c.label}</span>
                <span className="text-sm font-medium">{c.value}</span>
              </GlassCard>
            );
          })}
        </div>
      </div>
    </section>
  );
}
