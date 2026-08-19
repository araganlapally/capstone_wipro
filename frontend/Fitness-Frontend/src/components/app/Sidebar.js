import React from "react";
import { useNavigate, useLocation } from "react-router-dom";
import {
  Sparkles,
  LayoutDashboard,
  Target,
  Dumbbell,
  Salad,
  TrendingUp,
  Settings,
  User,
  LogOut,
} from "lucide-react";

const NAV_ITEMS = [
  { label: "Dashboard", icon: LayoutDashboard, path: "/dashboard" },
  { label: "Goal Setting", icon: Target, path: "/goals" },
  { label: "Workouts", icon: Dumbbell, path: "/workouts" },
  { label: "Nutrition", icon: Salad, path: "/nutrition" },
  { label: "AI Coach", icon: Sparkles, path: "/ai-coach" },
];

// Items that are shown for product-vision continuity but have no
// backing route/data yet. Rendered disabled with a "Soon" tag rather
// than as broken links.
const SOON_ITEMS = [
  { label: "Progress", icon: TrendingUp },
  { label: "Settings", icon: Settings },
];

export default function Sidebar() {
  const navigate = useNavigate();
  const location = useLocation();

  const logout = () => {
    localStorage.clear();
    navigate("/login");
  };

  return (
    <aside className="hidden md:flex flex-col w-[248px] shrink-0 h-screen sticky top-0 liquid-glass-strong !rounded-none border-r border-white/10 px-4 py-6">
      <button
        onClick={() => navigate("/dashboard")}
        className="flex items-center gap-2 px-2 mb-8 font-semibold tracking-tight"
      >
        <Sparkles size={18} className="text-[#4DFFB2]" aria-hidden="true" />
        FIT-AI
      </button>

      <nav className="flex-1 flex flex-col gap-1" aria-label="App navigation">
        {NAV_ITEMS.map((item) => {
          const Icon = item.icon;
          const active = location.pathname === item.path;
          return (
            <button
              key={item.label}
              onClick={() => navigate(item.path)}
              className={[
                "flex items-center gap-3 px-3 py-2.5 rounded-xl text-sm text-left transition-colors",
                active
                  ? "bg-[#4DFFB2]/12 text-[#4DFFB2] border border-[#4DFFB2]/25"
                  : "text-white/65 hover:text-white hover:bg-white/5 border border-transparent",
              ].join(" ")}
            >
              <Icon size={16} aria-hidden="true" />
              {item.label}
            </button>
          );
        })}

        {SOON_ITEMS.map((item) => {
          const Icon = item.icon;
          return (
            <div
              key={item.label}
              className="flex items-center gap-3 px-3 py-2.5 rounded-xl text-sm text-white/30 cursor-not-allowed"
              aria-disabled="true"
            >
              <Icon size={16} aria-hidden="true" />
              {item.label}
              <span className="ml-auto text-[9px] uppercase tracking-wide border border-white/15 rounded px-1.5 py-0.5">
                Soon
              </span>
            </div>
          );
        })}
      </nav>

      <div className="border-t border-white/10 pt-4 mt-4 flex flex-col gap-1">
        <button
          onClick={() => navigate("/profile")}
          className={[
            "flex items-center gap-3 px-3 py-2.5 rounded-xl text-sm text-left transition-colors",
            location.pathname === "/profile"
              ? "bg-[#4DFFB2]/12 text-[#4DFFB2] border border-[#4DFFB2]/25"
              : "text-white/65 hover:text-white hover:bg-white/5 border border-transparent",
          ].join(" ")}
        >
          <User size={16} aria-hidden="true" />
          Profile
        </button>
        <button
          onClick={logout}
          className="flex items-center gap-3 px-3 py-2.5 rounded-xl text-sm text-left text-red-400/80 hover:text-red-400 hover:bg-red-400/5 transition-colors"
        >
          <LogOut size={16} aria-hidden="true" />
          Log out
        </button>
      </div>
    </aside>
  );
}
