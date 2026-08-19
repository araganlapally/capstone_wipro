import React, { useMemo } from "react";
import { motion } from "framer-motion";

/**
 * GlassCard — reusable Liquid Glass surface.
 *
 * Props:
 *  - strong: use a more pronounced glass treatment (heavier blur/border)
 *  - glow: add a soft accent glow shadow
 *  - hover: enable hover elevation/scale micro-interaction
 *  - as: element/tag to render (default 'div')
 *  - className: extra tailwind classes
 */
export default function GlassCard({
  children,
  strong = false,
  glow = false,
  hover = true,
  className = "",
  as: Tag = "div",
  ...rest
}) {
  // IMPORTANT: motion(Tag) must be memoized. Creating it fresh on every
  // render produces a new component identity each time, which makes React
  // unmount/remount this element (and all its children, e.g. form inputs)
  // on every keystroke/re-render — causing inputs to lose focus and value.
  const MotionTag = useMemo(() => motion(Tag), [Tag]);

  return (
    <MotionTag
      className={[
        strong ? "liquid-glass-strong" : "liquid-glass",
        glow ? "liquid-glass-glow" : "",
        "rounded-3xl",
        className,
      ].join(" ")}
      whileHover={
        hover
          ? { y: -6, transition: { duration: 0.35, ease: "easeOut" } }
          : undefined
      }
      {...rest}
    >
      {children}
    </MotionTag>
  );
}
