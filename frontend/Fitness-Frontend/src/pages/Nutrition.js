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
            method: "POST"
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
    return <h2>Loading Nutrition Plan...</h2>;
  }

  return (
  <div
    style={{
      minHeight: "100vh",
      background: "#050b12",
      color: "white",
      padding: "30px",
    }}
  >
      <h1
        style={{
        color: "#22e68a",
        marginBottom: "30px",
      }}
>
       🥗 AI Nutrition Recommendation
      </h1>

      <div
  style={{
    display: "grid",
    gridTemplateColumns: "repeat(4, 1fr)",
    gap: "20px",
    marginBottom: "30px",
  }}
>
  <div
    style={{
      background: "#111827",
      padding: "20px",
      borderRadius: "16px",
    }}
  >
    <h3>🔥 Calories</h3>
    <h2>{nutrition.dailyCalories}</h2>
    <p>kcal</p>
  </div>

  <div
    style={{
      background: "#111827",
      padding: "20px",
      borderRadius: "16px",
    }}
  >
    <h3>💪 Protein</h3>
    <h2>{nutrition.dailyProtein}</h2>
    <p>g</p>
  </div>

  <div
    style={{
      background: "#111827",
      padding: "20px",
      borderRadius: "16px",
    }}
  >
    <h3>🍚 Carbs</h3>
    <h2>{nutrition.dailyCarbs}</h2>
    <p>g</p>
  </div>

  <div
    style={{
      background: "#111827",
      padding: "20px",
      borderRadius: "16px",
    }}
  >
    <h3>🥑 Fats</h3>
    <h2>{nutrition.dailyFats}</h2>
    <p>g</p>
  </div>
</div>

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