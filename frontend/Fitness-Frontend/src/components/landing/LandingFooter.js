import React from "react";
import { Link } from "react-router-dom";
import { Sparkles } from "lucide-react";

const LINKS = [
  { label: "Features", href: "#features" },
  { label: "AI Coach", href: "#ai-coach" },
  { label: "Workouts", href: "#workouts" },
  { label: "Nutrition", href: "#nutrition" },
  { label: "Progress", href: "#progress" },
];

export default function LandingFooter() {
  return (
    <footer className="relative border-t border-white/10 px-6 md:px-10 py-12">
      <div className="max-w-6xl mx-auto flex flex-col sm:flex-row justify-between gap-10">
        <div>
          <div className="flex items-center gap-2 font-semibold mb-2">
            <Sparkles size={16} className="text-[#4DFFB2]" aria-hidden="true" />
            FIT-AI
          </div>
          <p className="text-sm text-white/45 max-w-xs">
            Your adaptive AI fitness companion.
          </p>
        </div>

        <nav className="flex flex-wrap gap-x-8 gap-y-3 text-sm" aria-label="Footer navigation">
          {LINKS.map((l) => (
            <a
              key={l.label}
              href={l.href}
              className="text-white/55 hover:text-white transition-colors"
            >
              {l.label}
            </a>
          ))}
          <Link to="/login" className="text-white/55 hover:text-white transition-colors">
            Login
          </Link>
          <Link to="/register" className="text-white/55 hover:text-white transition-colors">
            Register
          </Link>
        </nav>
      </div>

      <p className="max-w-6xl mx-auto mt-10 text-xs text-white/25">
        © {new Date().getFullYear()} FIT-AI. All rights reserved.
      </p>
    </footer>
  );
}
