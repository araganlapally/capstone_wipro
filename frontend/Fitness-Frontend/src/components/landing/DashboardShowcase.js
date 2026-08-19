import React from "react";
import { motion } from "framer-motion";
import { Flame, Scale, Footprints, Gauge } from "lucide-react";
import GlassCard from "./GlassCard";

const METRICS = [
  { icon: Flame, label: "Calories Burned", value: "568", unit: "kcal", trend: "↑ 12% vs yesterday" },
  { icon: Scale, label: "Weight Progress", value: "72.4", unit: "kg", trend: "↓ 1.3 kg vs last week" },
  { icon: Footprints, label: "Daily Steps", value: "8,432", unit: "", trend: "↑ 15% vs yesterday" },
  { icon: Gauge, label: "AI Fitness Score", value: "86", unit: "/100", trend: "Excellent" },
];

export default function DashboardShowcase() {
  return (
    <section className="relative py-24 px-6 md:px-10 max-w-7xl mx-auto">
      <motion.div
        initial={{ opacity: 0, y: 24 }}
        whileInView={{ opacity: 1, y: 0 }}
        viewport={{ once: true, margin: "-80px" }}
        transition={{ duration: 0.6 }}
        className="text-center mb-14"
      >
        <h2 className="text-3xl sm:text-4xl font-semibold tracking-tight">
          Your fitness <span className="text-gradient-mint">command center.</span>
        </h2>
      </motion.div>

      <motion.div
        initial={{ opacity: 0, y: 40, rotateX: 6 }}
        whileInView={{ opacity: 1, y: 0, rotateX: 0 }}
        viewport={{ once: true, margin: "-100px" }}
        transition={{ duration: 0.8, ease: "easeOut" }}
        style={{ perspective: "1200px" }}
      >
        <GlassCard strong glow hover={false} className="p-6 sm:p-8">
          <div className="grid sm:grid-cols-2 lg:grid-cols-4 gap-4">
            {METRICS.map((m) => {
              const Icon = m.icon;
              return (
                <div
                  key={m.label}
                  className="rounded-2xl bg-white/5 border border-white/10 p-5"
                >
                  <div className="flex items-center gap-2 text-white/45 text-xs mb-3">
                    <Icon size={14} aria-hidden="true" />
                    {m.label}
                  </div>
                  <p className="text-2xl font-semibold">
                    {m.value}
                    <span className="text-sm text-white/45 ml-1">{m.unit}</span>
                  </p>
                  <p className="text-xs text-[#4DFFB2] mt-2">{m.trend}</p>
                </div>
              );
            })}
          </div>

          <p className="mt-6 text-center text-xs text-white/35">
            Preview of your real FIT-AI dashboard — accessible after login.
          </p>
        </GlassCard>
      </motion.div>
    </section>
  );
}
