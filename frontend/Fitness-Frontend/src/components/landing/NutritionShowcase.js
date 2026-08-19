import React from "react";
import { useNavigate } from "react-router-dom";
import { motion } from "framer-motion";
import { Coffee, Sun, Apple, Moon } from "lucide-react";
import GlassCard from "./GlassCard";
import GlassButton from "./GlassButton";

const MACROS = [
  { label: "Protein", current: 128, total: 150 },
  { label: "Carbs", current: 185, total: 250 },
  { label: "Fat", current: 56, total: 70 },
];

const MEALS = [
  { icon: Coffee, name: "Breakfast" },
  { icon: Sun, name: "Lunch" },
  { icon: Apple, name: "Snack" },
  { icon: Moon, name: "Dinner" },
];

function MacroBar({ label, current, total }) {
  const pct = Math.min(100, Math.round((current / total) * 100));
  return (
    <div>
      <div className="flex justify-between text-xs mb-1.5">
        <span className="text-white/60">{label}</span>
        <span className="text-white/80">
          {current}g <span className="text-white/40">/ {total}g</span>
        </span>
      </div>
      <div className="h-1.5 rounded-full bg-white/8 overflow-hidden">
        <div
          className="h-full rounded-full bg-[#4DFFB2]"
          style={{ width: `${pct}%` }}
        />
      </div>
    </div>
  );
}

export default function NutritionShowcase() {
  const navigate = useNavigate();

  return (
    <section id="nutrition" className="relative py-24 px-6 md:px-10 max-w-6xl mx-auto">
      <motion.div
        initial={{ opacity: 0, y: 24 }}
        whileInView={{ opacity: 1, y: 0 }}
        viewport={{ once: true, margin: "-80px" }}
        transition={{ duration: 0.6 }}
        className="text-center mb-14"
      >
        <h2 className="text-3xl sm:text-4xl font-semibold tracking-tight">
          Nutrition that <span className="text-gradient-mint">fits your goals.</span>
        </h2>
      </motion.div>

      <div className="grid lg:grid-cols-3 gap-6 items-stretch">
        <GlassCard strong glow className="lg:col-span-2 p-6 sm:p-7" hover={false}>
          <p className="text-xs uppercase tracking-wider text-white/45 mb-1">
            Today's Nutrition
          </p>
          <p className="text-3xl font-semibold mb-6">
            1,842 <span className="text-sm text-white/45">kcal</span>
          </p>

          <div className="space-y-4 mb-7">
            {MACROS.map((m) => (
              <MacroBar key={m.label} {...m} />
            ))}
          </div>

          <div className="grid grid-cols-4 gap-3">
            {MEALS.map((meal) => {
              const Icon = meal.icon;
              return (
                <div
                  key={meal.name}
                  className="rounded-xl bg-white/5 border border-white/10 p-3 flex flex-col items-center gap-1.5"
                >
                  <Icon size={16} className="text-[#4DFFB2]" aria-hidden="true" />
                  <span className="text-[11px] text-white/60">{meal.name}</span>
                </div>
              );
            })}
          </div>
        </GlassCard>

        <GlassCard className="p-6 sm:p-7 flex flex-col justify-between" hover={false}>
          <div>
            <p className="text-sm text-white/60 leading-relaxed">
              Nutrition plans generated with calories, macros, and meal
              recommendations tailored to your goals.
            </p>
          </div>
          <GlassButton
            variant="primary"
            className="w-full mt-6"
            onClick={() => navigate("/nutrition")}
          >
            Build My Nutrition Plan
          </GlassButton>
        </GlassCard>
      </div>
    </section>
  );
}
