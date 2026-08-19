import React from "react";
import { useNavigate } from "react-router-dom";
import { motion } from "framer-motion";
import { Sparkles } from "lucide-react";
import GlassCard from "./GlassCard";
import GlassButton from "./GlassButton";

const CONTEXT_METRICS = [
  { label: "Recovery", value: "84%" },
  { label: "Consistency", value: "91%" },
  { label: "Goal Progress", value: "76%" },
];

export default function AICoachShowcase() {
  const navigate = useNavigate();

  return (
    <section id="ai-coach" className="relative py-24 px-6 md:px-10 max-w-6xl mx-auto">
      <motion.div
        initial={{ opacity: 0, y: 24 }}
        whileInView={{ opacity: 1, y: 0 }}
        viewport={{ once: true, margin: "-80px" }}
        transition={{ duration: 0.6 }}
        className="text-center mb-14"
      >
        <h2 className="text-3xl sm:text-4xl font-semibold tracking-tight">
          A coach that <span className="text-gradient-mint">knows your journey.</span>
        </h2>
      </motion.div>

      <div className="grid lg:grid-cols-3 gap-6 items-stretch">
        {/* Chat interface */}
        <GlassCard strong glow className="lg:col-span-2 p-6 sm:p-7" hover={false}>
          <div className="flex items-center gap-2 mb-5">
            <Sparkles size={16} className="text-[#4DFFB2]" aria-hidden="true" />
            <span className="text-sm font-medium text-white/80">AI Coach</span>
            <span className="text-[10px] uppercase tracking-wide text-[#4DFFB2] border border-[#4DFFB2]/30 rounded px-1.5 py-0.5 ml-1">
              Beta
            </span>
          </div>

          <div className="space-y-3 mb-5">
            <div className="ml-auto max-w-[80%] bg-[#4DFFB2] text-[#03110B] rounded-2xl rounded-tr-sm px-4 py-2.5 text-sm">
              I feel tired today. What should I train?
            </div>
            <div className="mr-auto max-w-[85%] bg-white/8 border border-white/10 rounded-2xl rounded-tl-sm px-4 py-2.5 text-sm text-white/85">
              Based on your recent training, a lighter session may be more
              appropriate today. Consider reducing intensity and focusing on
              recovery.
            </div>
          </div>

          <GlassButton
            variant="primary"
            className="w-full sm:w-auto"
            onClick={() => navigate("/ai-coach")}
          >
            Talk to your AI Coach
          </GlassButton>
        </GlassCard>

        {/* Contextual metrics */}
        <div className="grid grid-cols-1 gap-4">
          {CONTEXT_METRICS.map((m) => (
            <GlassCard key={m.label} className="p-5 flex items-center justify-between" hover={false}>
              <span className="text-sm text-white/60">{m.label}</span>
              <span className="text-xl font-semibold text-gradient-mint">{m.value}</span>
            </GlassCard>
          ))}
        </div>
      </div>

      <p className="mt-4 text-center text-xs text-white/30">
        Sample conversation for illustration — connect to start your own with
        the real AI Coach.
      </p>
    </section>
  );
}
