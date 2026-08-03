import React, { useEffect, useState } from "react";

function Nutrition() {
  const [nutrition, setNutrition] = useState(null);

  useEffect(() => {
    const fetchNutrition = async () => {
      try {
        const token = localStorage.getItem("token");

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
            headers: {
              Authorization: `Bearer ${token}`
            }
          }
        );

        const data = await response.json();
        setNutrition(data);

      } catch (error) {
        console.error("Nutrition API Error:", error);
      }
    };

    fetchNutrition();
  }, []);

  if (!nutrition) {
    return <h2>Loading Nutrition Plan...</h2>;
  }

  return (
    <div style={{ padding: "20px" }}>
      <h1>AI Nutrition Recommendation</h1>

      <h2>Daily Nutrition Target</h2>

      <p>
        <strong>Calories:</strong> {nutrition.dailyCalories} kcal
      </p>

      <p>
        <strong>Protein:</strong> {nutrition.dailyProtein} g
      </p>

      <p>
        <strong>Carbs:</strong> {nutrition.dailyCarbs} g
      </p>

      <p>
        <strong>Fats:</strong> {nutrition.dailyFats} g
      </p>

      <hr />

      <h2>Vegetarian Foods 🌱</h2>

      {nutrition.vegetarianFoods?.map((food, index) => (
        <div key={index}>
          <h3>{food.food}</h3>

          <p>Quantity: {food.quantity}</p>
          <p>Calories: {food.calories}</p>
          <p>Protein: {food.protein} g</p>
          <p>Carbs: {food.carbs} g</p>
          <p>Fats: {food.fats} g</p>

          <hr />
        </div>
      ))}

      <h2>Non Vegetarian Foods 🍗</h2>

      {nutrition.nonVegetarianFoods?.map((food, index) => (
        <div key={index}>
          <h3>{food.food}</h3>

          <p>Quantity: {food.quantity}</p>
          <p>Calories: {food.calories}</p>
          <p>Protein: {food.protein} g</p>
          <p>Carbs: {food.carbs} g</p>
          <p>Fats: {food.fats} g</p>

          <hr />
        </div>
      ))}
    </div>
  );
}

export default Nutrition;