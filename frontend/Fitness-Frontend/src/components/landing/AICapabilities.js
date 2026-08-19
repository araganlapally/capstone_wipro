import React from "react";
import { useNavigate } from "react-router-dom";
import { motion } from "framer-motion";
import { Sparkles, Dumbbell, Salad, ArrowRight } from "lucide-react";
import GlassCard from "./GlassCard";

const CAPABILITIES = [
  {
    icon: Sparkles,
    title: "AI Coach",
    description:
      "Ask questions, get personalized fitness guidance, and understand your next best action.",
    cta: "Meet your AI Coach",
    route: "/ai-coach",
  },
  {
    icon: Dumbbell,
    title: "Adaptive Workouts",
    description:
      "Generate personalized workouts based on your goals, fitness profile and training needs.",
    cta: "Build My Workout",
    route: "/workouts",
  },
  {
    icon: Salad,
    title: "Personalized Nutrition",
    description:
      "Generate nutrition plans with calories, macros and meal recommendations tailored to your goals.",
    cta: "Plan My Nutrition",
    route: "/nutrition",
  },
];

export default function AICapabilities() {
  const navigate = useNavigate();

  return (
    <section id="features" className="relative py-24 px-6 md:px-10 max-w-7xl mx-auto">
      <motion.div
        initial={{ opacity: 0, y: 24 }}
        whileInView={{ opacity: 1, y: 0 }}
        viewport={{ once: true, margin: "-80px" }}
        transition={{ duration: 0.6 }}
        className="text-center mb-14"
      >
        <h2 className="text-3xl sm:text-4xl font-semibold tracking-tight">
          One intelligence. <span className="text-gradient-mint">Your entire fitness journey.</span>
        </h2>
      </motion.div>

      <div className="grid md:grid-cols-3 gap-6">
        {CAPABILITIES.map((cap, i) => {
          const Icon = cap.icon;
          return (
            <motion.div
              key={cap.title}
              initial={{ opacity: 0, y: 30 }}
              whileInView={{ opacity: 1, y: 0 }}
              viewport={{ once: true, margin: "-60px" }}
              transition={{ duration: 0.55, delay: i * 0.1 }}
            >
              <GlassCard className="p-7 h-full flex flex-col" glow={i === 0}>
                <div className="w-11 h-11 rounded-2xl bg-[#4DFFB2]/12 border border-[#4DFFB2]/25 flex items-center justify-center mb-5">
                  <Icon size={20} className="text-[#4DFFB2]" aria-hidden="true" />
                </div>
                <h3 className="text-xl font-semibold mb-2">{cap.title}</h3>
                <p className="text-sm text-white/60 leading-relaxed flex-1">
                  {cap.description}
                </p>
                <button
                  onClick={() => navigate(cap.route)}
                  className="mt-6 inline-flex items-center gap-1.5 text-sm font-medium text-[#4DFFB2] hover:gap-2.5 transition-all focus:outline-none focus-visible:ring-2 focus-visible:ring-[#4DFFB2] rounded"
                >
                  {cap.cta} <ArrowRight size={15} aria-hidden="true" />
                </button>
              </GlassCard>
            </motion.div>
          );
        })}
      </div>
    </section>
  );
}
