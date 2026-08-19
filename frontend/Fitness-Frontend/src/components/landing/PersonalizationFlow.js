import React from "react";
import { motion } from "framer-motion";
import { User, Target, Dumbbell, Salad, TrendingUp, Cpu, Sparkles } from "lucide-react";
import GlassCard from "./GlassCard";

const INPUTS = [
  { icon: User, label: "User Profile" },
  { icon: Target, label: "Goals" },
  { icon: Dumbbell, label: "Workout History" },
  { icon: Salad, label: "Nutrition" },
  { icon: TrendingUp, label: "Progress" },
];

export default function PersonalizationFlow() {
  return (
    <section className="relative py-24 px-6 md:px-10 max-w-6xl mx-auto">
      <motion.div
        initial={{ opacity: 0, y: 24 }}
        whileInView={{ opacity: 1, y: 0 }}
        viewport={{ once: true, margin: "-80px" }}
        transition={{ duration: 0.6 }}
        className="text-center mb-16"
      >
        <h2 className="text-3xl sm:text-4xl font-semibold tracking-tight">
          FIT-AI doesn't just generate. <span className="text-gradient-mint">It adapts.</span>
        </h2>
        <p className="mt-4 text-white/55 max-w-xl mx-auto">
          Every recommendation starts with you.
        </p>
      </motion.div>

      <div className="flex flex-col items-center gap-3">
        {/* Input nodes */}
        <div className="grid grid-cols-2 sm:grid-cols-5 gap-3 w-full">
          {INPUTS.map((inp, i) => {
            const Icon = inp.icon;
            return (
              <motion.div
                key={inp.label}
                initial={{ opacity: 0, y: 16 }}
                whileInView={{ opacity: 1, y: 0 }}
                viewport={{ once: true }}
                transition={{ duration: 0.5, delay: i * 0.08 }}
              >
                <GlassCard hover={false} className="p-4 flex flex-col items-center gap-2 text-center">
                  <Icon size={18} className="text-[#4DFFB2]" aria-hidden="true" />
                  <span className="text-xs text-white/70">{inp.label}</span>
                </GlassCard>
              </motion.div>
            );
          })}
        </div>

        {/* Connecting line */}
        <div className="relative h-14 w-px bg-gradient-to-b from-white/20 to-[#4DFFB2]/40 overflow-hidden">
          <span className="absolute left-1/2 -translate-x-1/2 top-0 w-1.5 h-1.5 rounded-full bg-[#4DFFB2]" style={{ animation: "flow-dot 2.2s ease-in-out infinite" }} />
        </div>

        {/* AI Engine node */}
        <GlassCard strong glow hover={false} className="px-8 py-4 flex items-center gap-3">
          <Cpu size={20} className="text-[#4DFFB2]" aria-hidden="true" />
          <span className="font-semibold tracking-tight">AI Engine</span>
        </GlassCard>

        <div className="relative h-14 w-px bg-gradient-to-b from-[#4DFFB2]/40 to-white/20 overflow-hidden">
          <span className="absolute left-1/2 -translate-x-1/2 top-0 w-1.5 h-1.5 rounded-full bg-[#4DFFB2]" style={{ animation: "flow-dot 2.2s ease-in-out infinite", animationDelay: "1.1s" }} />
        </div>

        {/* Output node */}
        <GlassCard hover={false} className="px-6 py-4 flex items-center gap-3">
          <Sparkles size={18} className="text-[#4DFFB2]" aria-hidden="true" />
          <span className="text-sm text-white/85">Personalized Recommendations</span>
        </GlassCard>
      </div>
    </section>
  );
}
