import React, { useState } from "react";
import "../App.css";
import { useNavigate, Link } from "react-router-dom";

const API_BASE = "http://localhost:8081/api";

export default function Register() {
  const [form, setForm] = useState({ name:"", email:"", password:"" });
  const navigate = useNavigate();

  const register = async () => {
    try {
      await fetch(`${API_BASE}/auth/register`, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({
          fullName: form.name,
          email: form.email,
          password: form.password
        }),
      });

      navigate("/");
    } catch {}
  };

  return (
  <div style={{ display: "flex", height: "100vh", background: "#0b111c" }}>

    {/* LEFT SIDE */}
    <div style={{
      flex: 1,
      position: "relative",
      backgroundImage: "url(https://images.unsplash.com/photo-1541534741688-6078c6bfb5c5)",     
     backgroundSize: "cover",
      backgroundPosition: "center top",
      minHeight: "100vh"  // ✅ FIX
    }}>
      <div style={{
        position: "absolute",
        bottom: "80px",
        left: "40px",
        color: "white"
      }}>
        <h1 style={{ fontSize: "34px" }}>
          Get Started.
        </h1>

        <h1 style={{ fontSize: "34px", color: "#22e68a" }}>
          Start Your Journey.
        </h1>

        <p style={{ color: "#ccc" }}>
          AI-powered fitness <br />
          Built for you
        </p>
      </div>
    </div>

    {/* RIGHT SIDE */}
    <div style={{
      flex: 1,
      display: "flex",
      justifyContent: "center",
      alignItems: "center"
    }}>

      <div style={{
         width: "320px",
  color: "black",
  background: "rgba(255,255,255,0.05)",
  padding: "30px",
  borderRadius: "12px",
  boxShadow: "0 0 20px rgba(0,0,0,0.5)",
  backdropFilter: "blur(10px)"
      }}>

        <h2 style={{ color: "#22e68a" }}>FITAI</h2>
        <p>Create your account</p>

        <input
          placeholder="Full Name"
          onChange={(e) => setForm({...form, name: e.target.value})}
          style={{ width: "100%", marginTop: "10px", padding: "10px" }}
        />

        <input
          placeholder="Email"
          onChange={(e) => setForm({...form, email: e.target.value})}
          style={{ width: "100%", marginTop: "10px", padding: "10px" }}
        />

        <input
          type="password"
          placeholder="Password"
          onChange={(e) => setForm({...form, password: e.target.value})}
          style={{ width: "100%", marginTop: "10px", padding: "10px" }}
        />

        <button
          onClick={register}
          style={{
            width: "100%",
            marginTop: "15px",
            padding: "10px",
            background: "#22e68a",
            border: "none",
            color: "black"
          }}
        >
          Register
        </button>

        <p style={{ marginTop: "10px" }}>
          Already have an account? <Link to="/">Login</Link>
        </p>

      </div>

    </div>

  </div>
);
}