import React from "react";
import { motion } from "framer-motion";
import {
  Dumbbell,
  Sparkles,
  Salad,
  TrendingUp,
  Target,
  History,
  PieChart,
  LayoutDashboard,
} from "lucide-react";
import GlassCard from "./GlassCard";

const FEATURES = [
  { icon: Dumbbell, title: "Personalized Workouts", desc: "Plans built around your goals and level.", span: "lg:col-span-2" },
  { icon: Sparkles, title: "AI Coach", desc: "Guidance whenever you need it." },
  { icon: Salad, title: "Nutrition Planning", desc: "Calories and macros, tailored." },
  { icon: TrendingUp, title: "Progress Analytics", desc: "Understand your trends over time.", span: "lg:col-span-2" },
  { icon: Target, title: "Goal Tracking", desc: "Stay aligned with your objective." },
  { icon: History, title: "Workout History", desc: "Every session, logged." },
  { icon: PieChart, title: "Macro Tracking", desc: "Protein, carbs and fat at a glance." },
  { icon: LayoutDashboard, title: "Fitness Dashboard", desc: "Everything in one command center.", span: "lg:col-span-2" },
];

export default function FeatureGrid() {
  return (
    <section className="relative py-24 px-6 md:px-10 max-w-6xl mx-auto">
      <motion.div
        initial={{ opacity: 0, y: 24 }}
        whileInView={{ opacity: 1, y: 0 }}
        viewport={{ once: true, margin: "-80px" }}
        transition={{ duration: 0.6 }}
        className="text-center mb-14"
      >
        <h2 className="text-3xl sm:text-4xl font-semibold tracking-tight">
          Everything you need. <span className="text-gradient-mint">Nothing you don't.</span>
        </h2>
      </motion.div>

      <div className="grid sm:grid-cols-2 lg:grid-cols-4 gap-5">
        {FEATURES.map((f, i) => {
          const Icon = f.icon;
          return (
            <motion.div
              key={f.title}
              initial={{ opacity: 0, y: 24 }}
              whileInView={{ opacity: 1, y: 0 }}
              viewport={{ once: true, margin: "-60px" }}
              transition={{ duration: 0.5, delay: (i % 4) * 0.08 }}
              className={f.span || ""}
            >
              <GlassCard className="p-5 h-full">
                <div className="w-9 h-9 rounded-xl bg-white/8 border border-white/10 flex items-center justify-center mb-4">
                  <Icon size={16} className="text-[#4DFFB2]" aria-hidden="true" />
                </div>
                <h3 className="text-sm font-semibold mb-1">{f.title}</h3>
                <p className="text-xs text-white/55">{f.desc}</p>
              </GlassCard>
            </motion.div>
          );
        })}
      </div>
    </section>
  );
}
