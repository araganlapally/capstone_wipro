import React from "react";
import "../components/landing/landing.css";

import GlassNavbar from "../components/landing/GlassNavbar";
import Hero from "../components/landing/Hero";
import AICapabilities from "../components/landing/AICapabilities";
import PersonalizationFlow from "../components/landing/PersonalizationFlow";
import DashboardShowcase from "../components/landing/DashboardShowcase";
import AICoachShowcase from "../components/landing/AICoachShowcase";
import WorkoutShowcase from "../components/landing/WorkoutShowcase";
import NutritionShowcase from "../components/landing/NutritionShowcase";
import ProgressShowcase from "../components/landing/ProgressShowcase";
import HowItWorks from "../components/landing/HowItWorks";
import FeatureGrid from "../components/landing/FeatureGrid";
import FinalCTA from "../components/landing/FinalCTA";
import LandingFooter from "../components/landing/LandingFooter";

/**
 * LandingPage — public root route ("/").
 *
 * Composed entirely of presentational marketing sections. All CTAs
 * route to real, existing application routes (/login, /register,
 * /dashboard, /workouts, /nutrition). No fake routes or backend
 * calls are introduced here — all metrics shown are static demo
 * values isolated to this page only.
 */
export default function LandingPage() {
  return (
    <div className="landing-root">
      <div className="landing-ambient-bg" aria-hidden="true" />
      <GlassNavbar />
      <main>
        <Hero />
        <AICapabilities />
        <PersonalizationFlow />
        <DashboardShowcase />
        <AICoachShowcase />
        <WorkoutShowcase />
        <NutritionShowcase />
        <ProgressShowcase />
        <HowItWorks />
        <FeatureGrid />
        <FinalCTA />
      </main>
      <LandingFooter />
    </div>
  );
}
