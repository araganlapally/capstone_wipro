import React from "react";
import { motion } from "framer-motion";
import { Sparkles, Activity, Dumbbell, Salad } from "lucide-react";
import GlassCard from "../landing/GlassCard";
import "../landing/landing.css";

const FLOATING_STATS = [
  { icon: Activity, label: "AI Fitness Score", value: "92" },
  { icon: Dumbbell, label: "Today's Workout", value: "Chest + Triceps" },
  { icon: Salad, label: "Calories", value: "1,842 kcal" },
];

/**
 * AuthShell — shared split-screen Liquid Glass shell for Login and
 * Register. Left panel is an ambient ai-product visual (no external
 * imagery / network dependency). Right panel hosts the actual form
 * passed in as children — form logic/state is untouched.
 */
export default function AuthShell({ titleLines, subtitle, children, footer }) {
  return (
    <div className="landing-root min-h-screen flex">
      <div className="landing-ambient-bg" aria-hidden="true" />

      {/* Left visual panel */}
      <div className="hidden lg:flex flex-1 relative items-center justify-center p-16 overflow-hidden">
        <div className="max-w-md relative z-10">
          <div className="inline-flex items-center gap-2 liquid-glass rounded-full px-4 py-1.5 mb-6 text-xs text-white/70">
            <Sparkles size={14} className="text-[#4DFFB2]" aria-hidden="true" />
            Adaptive AI Fitness Companion
          </div>
          <h1 className="text-4xl xl:text-5xl font-semibold leading-[1.1] tracking-tight mb-4">
            {titleLines.map((line, i) => (
              <span key={i} className={i === titleLines.length - 1 ? "text-gradient-mint" : ""}>
                {line}
                <br />
              </span>
            ))}
          </h1>
          <p className="text-white/60 max-w-sm">{subtitle}</p>

          <div className="mt-10 grid gap-3">
            {FLOATING_STATS.map((s, i) => {
              const Icon = s.icon;
              return (
                <motion.div
                  key={s.label}
                  initial={{ opacity: 0, x: -16 }}
                  animate={{ opacity: 1, x: 0 }}
                  transition={{ duration: 0.6, delay: 0.15 * i }}
                >
                  <GlassCard hover={false} className="px-4 py-3 flex items-center gap-3">
                    <Icon size={16} className="text-[#4DFFB2]" aria-hidden="true" />
                    <span className="text-xs text-white/50 flex-1">{s.label}</span>
                    <span className="text-sm font-medium">{s.value}</span>
                  </GlassCard>
                </motion.div>
              );
            })}
          </div>
        </div>
      </div>

      {/* Right form panel */}
      <div className="flex-1 flex items-center justify-center p-6 sm:p-10 relative z-10">
        <motion.div
          initial={{ opacity: 0, y: 16 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.5 }}
          className="w-full max-w-sm"
        >
          <GlassCard strong glow hover={false} className="p-8">
            <div className="flex items-center gap-2 mb-1">
              <Sparkles size={18} className="text-[#4DFFB2]" aria-hidden="true" />
              <span className="font-semibold tracking-tight">FIT-AI</span>
            </div>
            <p className="text-xs text-white/45 mb-6">AI Fitness Assistant</p>

            {children}

            {footer}
          </GlassCard>
        </motion.div>
      </div>
    </div>
  );
}
