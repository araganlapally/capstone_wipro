import React from "react";
import { motion } from "framer-motion";
import GlassCard from "./GlassCard";

const STRENGTH = [
  { lift: "Bench", change: "+12%" },
  { lift: "Squat", change: "+18%" },
  { lift: "Deadlift", change: "+15%" },
];

// Simple lightweight SVG sparkline — no charting dependency needed.
function WeightSparkline() {
  const points = [82, 80, 79, 77, 76, 75, 74];
  const w = 240;
  const h = 70;
  const max = Math.max(...points);
  const min = Math.min(...points);
  const step = w / (points.length - 1);
  const coords = points.map((p, i) => {
    const x = i * step;
    const y = h - ((p - min) / (max - min)) * h;
    return `${x},${y}`;
  });

  return (
    <svg viewBox={`0 0 ${w} ${h}`} className="w-full h-20" aria-hidden="true">
      <polyline
        points={coords.join(" ")}
        fill="none"
        stroke="#4DFFB2"
        strokeWidth="2.5"
        strokeLinecap="round"
        strokeLinejoin="round"
      />
      <circle
        cx={coords[coords.length - 1].split(",")[0]}
        cy={coords[coords.length - 1].split(",")[1]}
        r="4"
        fill="#4DFFB2"
      />
    </svg>
  );
}

export default function ProgressShowcase() {
  return (
    <section id="progress" className="relative py-24 px-6 md:px-10 max-w-6xl mx-auto">
      <motion.div
        initial={{ opacity: 0, y: 24 }}
        whileInView={{ opacity: 1, y: 0 }}
        viewport={{ once: true, margin: "-80px" }}
        transition={{ duration: 0.6 }}
        className="text-center mb-14"
      >
        <h2 className="text-3xl sm:text-4xl font-semibold tracking-tight">
          See the change. <span className="text-gradient-mint">Understand the why.</span>
        </h2>
      </motion.div>

      <div className="grid md:grid-cols-2 lg:grid-cols-4 gap-5">
        {/* Weight trend */}
        <GlassCard className="p-5 lg:col-span-2" hover={false}>
          <p className="text-xs text-white/45 mb-1">Weight Trend</p>
          <p className="text-lg font-semibold mb-2">
            82 kg <span className="text-white/40">→</span> 74 kg
          </p>
          <WeightSparkline />
        </GlassCard>

        {/* Strength progression */}
        <GlassCard className="p-5" hover={false}>
          <p className="text-xs text-white/45 mb-3">Strength Progression</p>
          <div className="space-y-2.5">
            {STRENGTH.map((s) => (
              <div key={s.lift} className="flex justify-between text-sm">
                <span className="text-white/70">{s.lift}</span>
                <span className="text-[#4DFFB2] font-medium">{s.change}</span>
              </div>
            ))}
          </div>
        </GlassCard>

        {/* Consistency + Goal progress */}
        <GlassCard className="p-5 flex flex-col justify-between gap-4" hover={false}>
          <div>
            <p className="text-xs text-white/45 mb-1">Consistency</p>
            <p className="text-xl font-semibold">5 / 6 <span className="text-xs text-white/45">workouts</span></p>
          </div>
          <div>
            <p className="text-xs text-white/45 mb-1">Goal Progress</p>
            <div className="h-1.5 rounded-full bg-white/8 overflow-hidden mb-1">
              <div className="h-full rounded-full bg-[#4DFFB2]" style={{ width: "82%" }} />
            </div>
            <span className="text-xs text-white/60">82%</span>
          </div>
        </GlassCard>
      </div>
    </section>
  );
}
