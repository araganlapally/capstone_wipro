import React, { useEffect, useState } from "react";

export default function Workouts() {
  const [days, setDays] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadWorkout();
  }, []);

  const loadWorkout = async () => {
    try {
      const user = JSON.parse(localStorage.getItem("user"));

      const response = await fetch(
        `http://localhost:8082/api/workouts/generate/${user.id}`,
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        }
      );

      const data = await response.json();

      const workoutJson = JSON.parse(data.workoutPlan);

      setDays(workoutJson.days || []);
    } catch (error) {
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  const openVideo = (exercise) => {
    const searchUrl =
      "https://www.youtube.com/results?search_query=" +
      encodeURIComponent(exercise);

    window.open(searchUrl, "_blank");
  };

  if (loading) {
    return (
      <div
        style={{
          minHeight: "100vh",
          background: "#050b12",
          color: "white",
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
        }}
      >
        Generating AI Workout...
      </div>
    );
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
        💪 AI Workout Plan
      </h1>

      {days.map((day, index) => (
        <div
          key={index}
          style={{
            background: "#111827",
            borderRadius: "20px",
            padding: "25px",
            marginBottom: "20px",
            border: "1px solid #1f2937",
          }}
        >
          <h2 style={{ color: "#22e68a" }}>
            {day.day}
          </h2>

          <h3 style={{ color: "#cbd5e1" }}>
            {day.focus}
          </h3>

          {day.exercises?.length > 0 ? (
            day.exercises.map((exercise, i) => (
              <div
                key={i}
                style={{
                  background: "#0b111c",
                  padding: "15px",
                  borderRadius: "12px",
                  marginTop: "10px",
                }}
              >
                <h4>{exercise.name}</h4>

                <p>
                  Sets: {exercise.sets} | Reps: {exercise.reps}
                </p>

                <button
                  onClick={() => openVideo(exercise.name)}
                  style={{
                    background: "#22e68a",
                    border: "none",
                    color: "#000",
                    padding: "10px 15px",
                    borderRadius: "8px",
                    cursor: "pointer",
                    fontWeight: "bold",
                  }}
                >
                  ▶ Watch Demo
                </button>
              </div>
            ))
          ) : (
            <p style={{ color: "#94a3b8" }}>
              Rest Day 😴
            </p>
          )}
        </div>
      ))}
    </div>
  );
}