import React, { useMemo } from "react";
import { motion } from "framer-motion";

/**
 * GlassButton — primary / secondary CTA button.
 *
 * variant: "primary" | "secondary" | "ghost"
 */
export default function GlassButton({
  children,
  variant = "primary",
  className = "",
  as: Tag = "button",
  ...rest
}) {
  // Memoized for the same reason as GlassCard — avoids remounting on
  // every parent re-render (e.g. while typing in a nearby input).
  const MotionTag = useMemo(() => motion(Tag), [Tag]);

  const base =
    "inline-flex items-center justify-center gap-2 rounded-full font-semibold " +
    "px-7 py-3.5 text-[15px] transition-colors duration-300 " +
    "focus:outline-none focus-visible:ring-2 focus-visible:ring-[#4DFFB2] focus-visible:ring-offset-2 focus-visible:ring-offset-black";

  const variants = {
    primary:
      "bg-[#4DFFB2] text-[#03110B] shadow-[0_0_30px_rgba(77,255,178,0.35)] hover:shadow-[0_0_45px_rgba(77,255,178,0.5)]",
    secondary:
      "liquid-glass text-white hover:bg-white/10",
    ghost:
      "text-white/80 hover:text-white bg-transparent px-4 py-2",
  };

  return (
    <MotionTag
      className={[base, variants[variant], className].join(" ")}
      whileHover={{ scale: 1.03 }}
      whileTap={{ scale: 0.97 }}
      {...rest}
    >
      {children}
    </MotionTag>
  );
}
