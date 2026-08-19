import React from "react";
import { useNavigate } from "react-router-dom";
import { motion } from "framer-motion";
import { Activity, Flame, Dumbbell, Sparkles } from "lucide-react";
import GlassCard from "./GlassCard";
import GlassButton from "./GlassButton";

const fadeUp = {
  hidden: { opacity: 0, y: 24 },
  show: (i = 0) => ({
    opacity: 1,
    y: 0,
    transition: { delay: 0.15 * i, duration: 0.7, ease: "easeOut" },
  }),
};

export default function Hero() {
  const navigate = useNavigate();

  return (
    <section className="relative pt-40 pb-28 px-6 md:px-10 max-w-7xl mx-auto">
      <div className="grid lg:grid-cols-2 gap-14 items-center">
        {/* Left: copy */}
        <div>
          <motion.div
            variants={fadeUp}
            initial="hidden"
            animate="show"
            custom={0}
            className="inline-flex items-center gap-2 liquid-glass rounded-full px-4 py-1.5 mb-6 text-xs text-white/70"
          >
            <Sparkles size={14} className="text-[#4DFFB2]" aria-hidden="true" />
            Adaptive AI Fitness Companion
          </motion.div>

          <motion.h1
            variants={fadeUp}
            initial="hidden"
            animate="show"
            custom={1}
            className="text-5xl sm:text-6xl lg:text-7xl font-semibold leading-[1.05] tracking-tight"
          >
            YOUR BODY.
            <br />
            YOUR DATA.
            <br />
            <span className="text-gradient-mint">YOUR AI.</span>
          </motion.h1>

          <motion.p
            variants={fadeUp}
            initial="hidden"
            animate="show"
            custom={2}
            className="mt-6 text-lg text-white/65 max-w-lg"
          >
            An adaptive fitness companion that turns your goals, progress,
            workouts and nutrition into a personalized fitness journey.
          </motion.p>

          <motion.div
            variants={fadeUp}
            initial="hidden"
            animate="show"
            custom={3}
            className="mt-9 flex flex-wrap items-center gap-4"
          >
            <GlassButton variant="primary" onClick={() => navigate("/register")}>
              Start Your Journey
            </GlassButton>
            <GlassButton
              variant="secondary"
              onClick={() =>
                document
                  .querySelector("#features")
                  ?.scrollIntoView({ behavior: "smooth" })
              }
            >
              Explore FIT-AI
            </GlassButton>
          </motion.div>
        </div>

        {/* Right: floating product preview */}
        <motion.div
          initial={{ opacity: 0, scale: 0.92, y: 20 }}
          animate={{ opacity: 1, scale: 1, y: 0 }}
          transition={{ duration: 0.9, ease: "easeOut", delay: 0.2 }}
          className="relative h-[460px] sm:h-[520px]"
        >
          {/* Main dashboard preview card */}
          <GlassCard
            strong
            glow
            hover={false}
            className="absolute inset-0 p-6 sm:p-7 animate-float-slow"
          >
            <div className="flex items-center justify-between mb-5">
              <span className="text-xs uppercase tracking-wider text-white/45">
                Fitness Overview
              </span>
              <span className="flex items-center gap-1.5 text-xs text-[#4DFFB2]">
                <span className="w-1.5 h-1.5 rounded-full bg-[#4DFFB2] animate-pulse-glow" />
                Live
              </span>
            </div>

            <div className="grid grid-cols-2 gap-3 mb-4">
              <div className="rounded-2xl bg-white/5 border border-white/10 p-4">
                <p className="text-xs text-white/45 mb-1">AI Fitness Score</p>
                <p className="text-3xl font-semibold text-gradient-mint">92</p>
              </div>
              <div className="rounded-2xl bg-white/5 border border-white/10 p-4">
                <p className="text-xs text-white/45 mb-1">Weight</p>
                <p className="text-3xl font-semibold">72.4<span className="text-sm text-white/45"> kg</span></p>
              </div>
            </div>

            <div className="rounded-2xl bg-white/5 border border-white/10 p-4 mb-4">
              <p className="text-xs text-white/45 mb-1">Today's Workout</p>
              <p className="text-base font-medium flex items-center gap-2">
                <Dumbbell size={16} className="text-[#4DFFB2]" /> Chest + Triceps
              </p>
            </div>

            <div className="grid grid-cols-2 gap-3 mb-4">
              <div className="rounded-2xl bg-white/5 border border-white/10 p-4">
                <p className="text-xs text-white/45 mb-1 flex items-center gap-1">
                  <Flame size={12} /> Calories
                </p>
                <p className="text-xl font-semibold">1,842 <span className="text-xs text-white/45">kcal</span></p>
              </div>
              <div className="rounded-2xl bg-white/5 border border-white/10 p-4">
                <p className="text-xs text-white/45 mb-1 flex items-center gap-1">
                  <Activity size={12} /> Protein
                </p>
                <p className="text-xl font-semibold">128 <span className="text-xs text-white/45">g</span></p>
              </div>
            </div>

            <div className="rounded-2xl bg-[#4DFFB2]/10 border border-[#4DFFB2]/25 p-4">
              <p className="text-xs text-[#4DFFB2] mb-1 font-medium">AI Coach</p>
              <p className="text-sm text-white/80">"You're ready for today's workout."</p>
            </div>
          </GlassCard>

          {/* Floating accent card */}
          <GlassCard
            hover={false}
            className="hidden sm:flex absolute -left-8 top-10 w-40 p-4 animate-float-slower flex-col gap-1"
          >
            <p className="text-[11px] text-white/45">Recovery</p>
            <p className="text-2xl font-semibold text-gradient-mint">84%</p>
          </GlassCard>

          <GlassCard
            hover={false}
            className="hidden sm:flex absolute -right-6 bottom-6 w-44 p-4 animate-float-slower flex-col gap-1"
            style={{ animationDelay: "1.2s" }}
          >
            <p className="text-[11px] text-white/45">Goal Progress</p>
            <p className="text-2xl font-semibold text-gradient-mint">76%</p>
          </GlassCard>
        </motion.div>
      </div>
    </section>
  );
}
