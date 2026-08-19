import React from "react";
import "../landing/landing.css";
import Sidebar from "./Sidebar";
import MobileTopbar from "./MobileTopbar";

/**
 * AppShell — shared authenticated layout wrapper.
 *
 * Wraps Dashboard / Goals / Workouts / Nutrition / Profile with a
 * consistent Liquid Glass sidebar (desktop) + collapsible top bar
 * (mobile). Purely presentational — does not touch any page's
 * business logic, state, or API calls.
 */
export default function AppShell({ children }) {
  return (
    <div className="landing-root min-h-screen flex">
      <div className="landing-ambient-bg" aria-hidden="true" />
      <Sidebar />
      <div className="flex-1 min-w-0 flex flex-col relative z-10">
        <MobileTopbar />
        <main className="flex-1 min-w-0">{children}</main>
      </div>
    </div>
  );
}
