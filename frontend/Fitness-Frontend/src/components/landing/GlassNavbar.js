import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { motion, AnimatePresence } from "framer-motion";
import { Menu, X, Sparkles } from "lucide-react";
import GlassButton from "./GlassButton";

const NAV_LINKS = [
  { label: "Features", href: "#features" },
  { label: "AI Coach", href: "#ai-coach" },
  { label: "Workouts", href: "#workouts" },
  { label: "Nutrition", href: "#nutrition" },
  { label: "Progress", href: "#progress" },
];

export default function GlassNavbar() {
  const [scrolled, setScrolled] = useState(false);
  const [mobileOpen, setMobileOpen] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    const onScroll = () => setScrolled(window.scrollY > 24);
    window.addEventListener("scroll", onScroll, { passive: true });
    return () => window.removeEventListener("scroll", onScroll);
  }, []);

  const scrollTo = (href) => {
    setMobileOpen(false);
    const el = document.querySelector(href);
    if (el) el.scrollIntoView({ behavior: "smooth", block: "start" });
  };

  return (
    <header className="fixed top-0 left-0 right-0 z-50 flex justify-center px-4 pt-4">
      <motion.nav
        initial={{ y: -30, opacity: 0 }}
        animate={{ y: 0, opacity: 1 }}
        transition={{ duration: 0.6, ease: "easeOut" }}
        className={[
          "liquid-glass-strong w-full max-w-6xl rounded-full flex items-center justify-between",
          "px-5 transition-all duration-300",
          scrolled ? "py-2.5" : "py-3.5",
        ].join(" ")}
        aria-label="Primary navigation"
      >
        {/* Logo */}
        <button
          onClick={() => navigate("/")}
          className="flex items-center gap-2 font-semibold tracking-tight text-white"
        >
          <Sparkles size={18} className="text-[#4DFFB2]" aria-hidden="true" />
          <span>FIT-AI</span>
        </button>

        {/* Desktop links */}
        <div className="hidden md:flex items-center gap-1">
          {NAV_LINKS.map((link) => (
            <button
              key={link.href}
              onClick={() => scrollTo(link.href)}
              className="px-4 py-2 text-sm text-white/65 hover:text-white transition-colors rounded-full focus:outline-none focus-visible:ring-2 focus-visible:ring-[#4DFFB2]"
            >
              {link.label}
            </button>
          ))}
        </div>

        {/* Desktop CTAs */}
        <div className="hidden md:flex items-center gap-3">
          <button
            onClick={() => navigate("/login")}
            className="px-4 py-2 text-sm text-white/80 hover:text-white transition-colors"
          >
            Login
          </button>
          <GlassButton
            variant="primary"
            className="!px-5 !py-2.5 !text-sm"
            onClick={() => navigate("/register")}
          >
            Get Started
          </GlassButton>
        </div>

        {/* Mobile toggle */}
        <button
          className="md:hidden p-2 text-white"
          aria-label={mobileOpen ? "Close menu" : "Open menu"}
          aria-expanded={mobileOpen}
          onClick={() => setMobileOpen((v) => !v)}
        >
          {mobileOpen ? <X size={22} /> : <Menu size={22} />}
        </button>
      </motion.nav>

      {/* Mobile menu */}
      <AnimatePresence>
        {mobileOpen && (
          <motion.div
            initial={{ opacity: 0, y: -10 }}
            animate={{ opacity: 1, y: 0 }}
            exit={{ opacity: 0, y: -10 }}
            transition={{ duration: 0.25 }}
            className="liquid-glass-strong fixed top-20 left-4 right-4 z-40 rounded-3xl p-5 md:hidden"
          >
            <div className="flex flex-col gap-1">
              {NAV_LINKS.map((link) => (
                <button
                  key={link.href}
                  onClick={() => scrollTo(link.href)}
                  className="text-left px-3 py-3 rounded-xl text-white/80 hover:text-white hover:bg-white/5 transition-colors"
                >
                  {link.label}
                </button>
              ))}
            </div>
            <div className="mt-3 flex flex-col gap-2 border-t border-white/10 pt-4">
              <button
                onClick={() => {
                  setMobileOpen(false);
                  navigate("/login");
                }}
                className="w-full text-center px-4 py-3 rounded-full text-white/85 hover:text-white transition-colors"
              >
                Login
              </button>
              <GlassButton
                variant="primary"
                className="w-full"
                onClick={() => {
                  setMobileOpen(false);
                  navigate("/register");
                }}
              >
                Get Started
              </GlassButton>
            </div>
          </motion.div>
        )}
      </AnimatePresence>
    </header>
  );
}
