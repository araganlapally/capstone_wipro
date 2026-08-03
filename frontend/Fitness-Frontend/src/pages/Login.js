import React, { useState } from "react";
import "../App.css";
import { useNavigate, Link } from "react-router-dom";

const API_BASE = "http://localhost:8081/api";

export default function Login() {
  const [form, setForm] = useState({ email: "", password: "" });
  const [msg, setMsg] = useState("");
  const navigate = useNavigate();

  const login = async () => {
    try {
      const res = await fetch(`${API_BASE}/auth/login`, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(form),
      });

      const data = await res.json();

      const token = data.token || data.jwt;
      if (!token) throw new Error("No token");

      localStorage.setItem("token", token);

      localStorage.setItem("user", JSON.stringify({
  id: data.id,
  fullName: data.fullName,
  email: data.email
}));

      navigate("/dashboard");
    } catch {
      setMsg("Invalid credentials");
    }
  };

  return (
  <div style={{ display: "flex", height: "100vh", background: "#0b111c" }}>

    {/* LEFT SIDE IMAGE */}
    <div style={{
      flex: 1,
      backgroundImage: "url('https://images.unsplash.com/photo-1579758629938-03607ccdbaba')",
      backgroundSize: "cover",
      backgroundPosition: "center"
    }}>
      <div style={{ padding: "40px", color: "white" }}>
        <h1 style={{
  fontSize: "36px",
  fontWeight: "700",
  lineHeight: "1.3"
}}>
  Build Your Strength.
</h1>

<h1 style={{
  fontSize: "36px",
  fontWeight: "700",
  color: "#22e68a"
}}>
  Transform Your Body.
</h1>

<p style={{
  marginTop: "15px",
  color: "#d8e7e9",
  fontSize: "20px"
}}>
  AI-powered workouts<br />
  Personalized plans for you
</p>

      </div>
    </div>

    {/* RIGHT SIDE LOGIN FORM */}
    <div style={{
      flex: 1,
      display: "flex",
      justifyContent: "center",
      alignItems: "center"
    }}>
      
<div style={{
  width: "320px",
  color: "white",
  background: "rgba(255,255,255,0.05)",
  padding: "30px",
  borderRadius: "12px",
  boxShadow: "0 0 20px rgba(0,0,0,0.5)",
  backdropFilter: "blur(10px)"
}}>


        <h2 style={{
  color: "#22e68a",
  fontWeight: "bold",
  letterSpacing: "2px",
  marginBottom: "5px"
}}>
  FITAI
</h2>

<p style={{
  color: "#aaa",
  fontSize: "12px",
  marginBottom: "15px"
}}>
  AI Fitness Assistant
</p>


        {msg && <p style={{ color: "red" }}>{msg}</p>}

        
<input
  placeholder="Email"
  onChange={(e) => setForm({ ...form, email: e.target.value })}
  style={{
    width: "100%",
    padding: "10px",
    marginTop: "10px",
    borderRadius: "6px",
    background: "#111827",
    color: "white",   // ✅ THIS FIXES VISIBILITY
    border: "1px solid #333"
  }}
/>

       
<input
  type="password"
  placeholder="Password"
  onChange={(e) => setForm({ ...form, password: e.target.value })}
  style={{
    width: "100%",
    padding: "10px",
    marginTop: "10px",
    borderRadius: "6px",
    background: "#111827",
    color: "white",   // ✅ important
    border: "1px solid #333"
  }}
/>

        <button
          onClick={login}
          style={{
            width: "100%",
            padding: "10px",
            marginTop: "10px",
            background: "#22e68a",
            border: "none",
            borderRadius: "6px"
          }}
        >
          Login
        </button>

        <p style={{ marginTop: "10px" }}>
          Don’t have an account? <Link to="/register">Register</Link>
        </p>

      </div>
    </div>

  </div>
);
}