
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

export default function Goals() {
  const navigate = useNavigate();

  const [profile, setProfile] = useState({
    age: "",
    height: "",
    weight: "",
    gender: "",
    goal: ""
  });

  const user = JSON.parse(localStorage.getItem("user"));
  const token = localStorage.getItem("token");

  useEffect(() => {
    fetchProfile();
  }, []);

  const fetchProfile = async () => {
    try {
      const res = await fetch(
        `http://localhost:8081/api/users/${user.id}/profile`,
        {
          headers: {
            Authorization: `Bearer ${token}`
          }
        }
      );

      if (res.ok) {
        const data = await res.json();

        setProfile({
          age: data.age || "",
          height: data.height || "",
          weight: data.weight || "",
          gender: data.gender || "",
          goal: data.goal || ""
        });
      }
    } catch (error) {
      console.error(error);
    }
  };

  const saveProfile = async () => {
    try {
      const res = await fetch(
        `http://localhost:8081/api/users/${user.id}/profile`,
        {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`
          },
          body: JSON.stringify(profile)
        }
      );

      if (res.ok) {
        alert("Goals Saved Successfully ✅");
        navigate("/dashboard");
      } else {
        alert("Failed to save goals");
      }
    } catch (error) {
      console.error(error);
      alert("Error saving goals");
    }
  };

  return (
    <div
      style={{
        minHeight: "100vh",
        background: "#050b12",
        color: "white",
        padding: "40px"
      }}
    >
      <h1 style={{ color: "#22e68a" }}>
        🎯 Goal Setting
      </h1>

      <div
        style={{
          maxWidth: "500px",
          background: "#111827",
          padding: "30px",
          borderRadius: "16px",
          border: "1px solid #1f2937"
        }}
      >
        <div style={{ marginBottom: "15px" }}>
          <label>Age</label>
          <input
            type="number"
            value={profile.age}
            onChange={(e) =>
              setProfile({
                ...profile,
                age: e.target.value
              })
            }
            style={inputStyle}
          />
        </div>

        <div style={{ marginBottom: "15px" }}>
          <label>Height (cm)</label>
          <input
            type="number"
            value={profile.height}
            onChange={(e) =>
              setProfile({
                ...profile,
                height: e.target.value
              })
            }
            style={inputStyle}
          />
        </div>

        <div style={{ marginBottom: "15px" }}>
          <label>Weight (kg)</label>
          <input
            type="number"
            value={profile.weight}
            onChange={(e) =>
              setProfile({
                ...profile,
                weight: e.target.value
              })
            }
            style={inputStyle}
          />
        </div>

        <div style={{ marginBottom: "15px" }}>
          <label>Gender</label>
          <select
            value={profile.gender}
            onChange={(e) =>
              setProfile({
                ...profile,
                gender: e.target.value
              })
            }
            style={inputStyle}
          >
            <option value="">Select Gender</option>
            <option value="Male">Male</option>
            <option value="Female">Female</option>
          </select>
        </div>

        <div style={{ marginBottom: "20px" }}>
          <label>Goal</label>
          <select
            value={profile.goal}
            onChange={(e) =>
              setProfile({
                ...profile,
                goal: e.target.value
              })
            }
            style={inputStyle}
          >
            <option value="">Select Goal</option>
            <option value="Weight Loss">Weight Loss</option>
            <option value="Weight Gain">Weight Gain</option>
            <option value="Muscle Building">Muscle Building</option>
            <option value="General Fitness">General Fitness</option>
            <option value="Body Strengthening">
              Body Strengthening
            </option>
          </select>
        </div>

        <button
          onClick={saveProfile}
          style={{
            background: "#22e68a",
            color: "#000",
            border: "none",
            padding: "12px 20px",
            borderRadius: "10px",
            fontWeight: "bold",
            cursor: "pointer",
            width: "100%"
          }}
        >
          Save Goals
        </button>
      </div>
    </div>
  );
}

const inputStyle = {
  width: "100%",
  padding: "12px",
  marginTop: "6px",
  borderRadius: "10px",
  border: "1px solid #334155",
  background: "#0b111c",
  color: "white"
};