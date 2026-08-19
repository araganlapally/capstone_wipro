import React, { useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { AnimatePresence, motion } from "framer-motion";
import {
  Sparkles,
  Menu,
  X,
  LayoutDashboard,
  Target,
  Dumbbell,
  Salad,
  User,
  LogOut,
} from "lucide-react";

const NAV_ITEMS = [
  { label: "Dashboard", icon: LayoutDashboard, path: "/dashboard" },
  { label: "Goal Setting", icon: Target, path: "/goals" },
  { label: "Workouts", icon: Dumbbell, path: "/workouts" },
  { label: "Nutrition", icon: Salad, path: "/nutrition" },
  { label: "AI Coach", icon: Sparkles, path: "/ai-coach" },
  { label: "Profile", icon: User, path: "/profile" },
];

/**
 * MobileTopbar — collapses the app sidebar into a floating glass
 * top bar + slide-down menu on small screens.
 */
export default function MobileTopbar() {
  const [open, setOpen] = useState(false);
  const navigate = useNavigate();
  const location = useLocation();

  const logout = () => {
    localStorage.clear();
    navigate("/login");
  };

  return (
    <div className="md:hidden sticky top-0 z-40">
      <div className="liquid-glass-strong flex items-center justify-between px-4 py-3 border-b border-white/10">
        <button
          onClick={() => navigate("/dashboard")}
          className="flex items-center gap-2 font-semibold tracking-tight text-sm"
        >
          <Sparkles size={16} className="text-[#4DFFB2]" aria-hidden="true" />
          FIT-AI
        </button>
        <button
          onClick={() => setOpen((v) => !v)}
          aria-label={open ? "Close menu" : "Open menu"}
          aria-expanded={open}
          className="p-2 text-white"
        >
          {open ? <X size={20} /> : <Menu size={20} />}
        </button>
      </div>

      <AnimatePresence>
        {open && (
          <motion.div
            initial={{ opacity: 0, y: -10 }}
            animate={{ opacity: 1, y: 0 }}
            exit={{ opacity: 0, y: -10 }}
            transition={{ duration: 0.2 }}
            className="liquid-glass-strong mx-3 mt-2 rounded-2xl p-3 absolute left-0 right-0"
          >
            {NAV_ITEMS.map((item) => {
              const Icon = item.icon;
              const active = location.pathname === item.path;
              return (
                <button
                  key={item.label}
                  onClick={() => {
                    setOpen(false);
                    navigate(item.path);
                  }}
                  className={[
                    "flex items-center gap-3 w-full px-3 py-2.5 rounded-xl text-sm text-left transition-colors",
                    active ? "text-[#4DFFB2]" : "text-white/75",
                  ].join(" ")}
                >
                  <Icon size={16} aria-hidden="true" />
                  {item.label}
                </button>
              );
            })}
            <button
              onClick={logout}
              className="flex items-center gap-3 w-full px-3 py-2.5 rounded-xl text-sm text-left text-red-400/80 mt-1 border-t border-white/10 pt-3"
            >
              <LogOut size={16} aria-hidden="true" />
              Log out
            </button>
          </motion.div>
        )}
      </AnimatePresence>
    </div>
  );
}
