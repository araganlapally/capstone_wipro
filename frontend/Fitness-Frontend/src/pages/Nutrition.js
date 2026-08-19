import React, { useEffect, useState } from "react";
import { Salad, Flame, Beef, Wheat, Nut } from "lucide-react";
import AppShell from "../components/app/AppShell";
import GlassCard from "../components/landing/GlassCard";

function Nutrition() {
  const [nutrition, setNutrition] = useState(null);

  useEffect(() => {
    const fetchNutrition = async () => {
      try {
        const storedUser = localStorage.getItem("user");

        console.log("Stored User:", storedUser);

        const user = JSON.parse(storedUser);

        const userId = user?.id || user?.userId;

        console.log("UserId:", userId);

        if (!userId) {
          console.error("User ID not found");
          return;
        }
        const response = await fetch(
          `http://localhost:8082/api/meals/generate/${userId}`,
          {
            method: "POST",
          }
        );

        const data = await response.json();
        console.log("NUTRITION DATA =", data);
        setNutrition(data);
      } catch (error) {
        console.error("Nutrition API Error:", error);
      }
    };

    fetchNutrition();
  }, []);

  if (!nutrition) {
    return (
      <AppShell>
        <div className="min-h-[80vh] flex items-center justify-center text-white/60 text-sm">
          Loading Nutrition Plan...
        </div>
      </AppShell>
    );
  }

  const SUMMARY = [
    { icon: Flame, label: "Calories", value: nutrition.dailyCalories, unit: "kcal" },
    { icon: Beef, label: "Protein", value: nutrition.dailyProtein, unit: "g" },
    { icon: Wheat, label: "Carbs", value: nutrition.dailyCarbs, unit: "g" },
    { icon: Nut, label: "Fats", value: nutrition.dailyFats, unit: "g" },
  ];

  return (
    <AppShell>
      <div className="px-5 sm:px-8 py-8 max-w-5xl mx-auto">
        <h1 className="text-2xl font-semibold flex items-center gap-2 mb-8">
          <Salad size={22} className="text-[#4DFFB2]" aria-hidden="true" />
          AI Nutrition Recommendation
        </h1>

        <div className="grid grid-cols-2 lg:grid-cols-4 gap-4 mb-8">
          {SUMMARY.map((s) => {
            const Icon = s.icon;
            return (
              <GlassCard key={s.label} className="p-5" hover={false}>
                <div className="flex items-center gap-2 text-white/45 text-xs mb-2">
                  <Icon size={14} aria-hidden="true" />
                  {s.label}
                </div>
                <p className="text-2xl font-semibold">
                  {s.value} <span className="text-xs text-white/45">{s.unit}</span>
                </p>
              </GlassCard>
            );
          })}
        </div>

        <h2 className="text-lg font-semibold mb-4">Vegetarian Foods 🌱</h2>
        <div className="grid sm:grid-cols-2 gap-4 mb-8">
          {nutrition.vegetarianFoods?.map((food, index) => (
            <GlassCard key={index} className="p-4" hover={false}>
              <h3 className="font-medium mb-2">{food.food}</h3>
              <div className="text-sm text-white/60 space-y-0.5">
                <p>Quantity: {food.quantity}</p>
                <p>Calories: {food.calories}</p>
                <p>Protein: {food.protein} g</p>
                <p>Carbs: {food.carbs} g</p>
                <p>Fats: {food.fats} g</p>
              </div>
            </GlassCard>
          ))}
        </div>

        <h2 className="text-lg font-semibold mb-4">Non Vegetarian Foods 🍗</h2>
        <div className="grid sm:grid-cols-2 gap-4">
          {nutrition.nonVegetarianFoods?.map((food, index) => (
            <GlassCard key={index} className="p-4" hover={false}>
              <h3 className="font-medium mb-2">{food.food}</h3>
              <div className="text-sm text-white/60 space-y-0.5">
                <p>Quantity: {food.quantity}</p>
                <p>Calories: {food.calories}</p>
                <p>Protein: {food.protein} g</p>
                <p>Carbs: {food.carbs} g</p>
                <p>Fats: {food.fats} g</p>
              </div>
            </GlassCard>
          ))}
        </div>
      </div>
    </AppShell>
  );
}

export default Nutrition;
