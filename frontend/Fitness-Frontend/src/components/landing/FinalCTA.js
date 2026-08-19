import React from "react";
import { useNavigate } from "react-router-dom";
import { motion } from "framer-motion";
import GlassButton from "./GlassButton";

export default function FinalCTA() {
  const navigate = useNavigate();

  return (
    <section className="relative py-28 px-6 md:px-10 max-w-5xl mx-auto text-center">
      <div
        className="absolute inset-0 -z-10 pointer-events-none"
        style={{
          background:
            "radial-gradient(50% 60% at 50% 50%, rgba(77,255,178,0.10), transparent 70%)",
        }}
      />
      <motion.div
        initial={{ opacity: 0, y: 24 }}
        whileInView={{ opacity: 1, y: 0 }}
        viewport={{ once: true, margin: "-80px" }}
        transition={{ duration: 0.6 }}
      >
        <h2 className="text-4xl sm:text-5xl font-semibold tracking-tight mb-4">
          Your next version <span className="text-gradient-mint">starts here.</span>
        </h2>
        <p className="text-white/60 mb-10 max-w-md mx-auto">
          Personalized fitness. Intelligent coaching. One platform.
        </p>
        <GlassButton
          variant="primary"
          className="!px-10 !py-4 !text-base"
          onClick={() => navigate("/register")}
        >
          Start Your Journey
        </GlassButton>
      </motion.div>
    </section>
  );
}
