import React from "react";
import { motion } from "framer-motion";
import { UserPlus, Target, Activity, Sparkles } from "lucide-react";
import GlassCard from "./GlassCard";

const STEPS = [
  { n: "01", icon: UserPlus, title: "Tell us about yourself" },
  { n: "02", icon: Target, title: "Set your goals" },
  { n: "03", icon: Activity, title: "Train and track" },
  { n: "04", icon: Sparkles, title: "Let FIT-AI adapt" },
];

export default function HowItWorks() {
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
          How <span className="text-gradient-mint">FIT-AI</span> works.
        </h2>
      </motion.div>

      <div className="grid sm:grid-cols-2 lg:grid-cols-4 gap-5 relative">
        {STEPS.map((step, i) => {
          const Icon = step.icon;
          return (
            <motion.div
              key={step.n}
              initial={{ opacity: 0, y: 24 }}
              whileInView={{ opacity: 1, y: 0 }}
              viewport={{ once: true, margin: "-60px" }}
              transition={{ duration: 0.5, delay: i * 0.1 }}
              className="relative"
            >
              <GlassCard className="p-6 h-full" hover={false}>
                <span className="text-xs font-mono text-white/30">{step.n}</span>
                <div className="w-10 h-10 rounded-xl bg-[#4DFFB2]/12 border border-[#4DFFB2]/25 flex items-center justify-center my-4">
                  <Icon size={18} className="text-[#4DFFB2]" aria-hidden="true" />
                </div>
                <p className="text-sm font-medium text-white/85">{step.title}</p>
              </GlassCard>
              {i < STEPS.length - 1 && (
                <div className="hidden lg:block absolute top-1/2 -right-3 w-6 h-px bg-white/15" />
              )}
            </motion.div>
          );
        })}
      </div>
    </section>
  );
}
