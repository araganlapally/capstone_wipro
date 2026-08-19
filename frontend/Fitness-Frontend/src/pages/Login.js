import React, { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import AuthShell from "../components/app/AuthShell";
import GlassButton from "../components/landing/GlassButton";

const API_BASE = "http://localhost:8081/api";

export default function Login() {
  const [form, setForm] = useState({ email: "", password: "" });
  const [msg, setMsg] = useState("");
  const navigate = useNavigate();

  const login = async () => {
    try {
      const res = await fetch(`${API_BASE}/auth/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(form),
      });

      const data = await res.json();
      console.log("LOGIN RESPONSE:", JSON.stringify(data));

      const token = data.token || data.jwt;
      if (!token) throw new Error("No token");

      localStorage.setItem("token", token);

      localStorage.setItem(
        "user",
        JSON.stringify({
          id: data.id,
          fullName: data.fullName,
          email: data.email,
        })
      );

      navigate("/dashboard");
    } catch {
      setMsg("Invalid credentials");
    }
  };

  const inputClass =
    "w-full px-4 py-3 rounded-xl bg-white/5 border border-white/10 text-white " +
    "placeholder:text-white/35 focus:outline-none focus:border-[#4DFFB2]/50 " +
    "focus:ring-1 focus:ring-[#4DFFB2]/30 transition-colors";

  return (
    <AuthShell
      titleLines={["Build Your Strength.", "Transform Your Body."]}
      subtitle="AI-powered workouts. Personalized plans for you."
      footer={
        <p className="mt-5 text-sm text-white/55 text-center">
          Don't have an account?{" "}
          <Link to="/register" className="text-[#4DFFB2] font-medium hover:underline">
            Register
          </Link>
        </p>
      }
    >
      {msg && (
        <p className="text-sm text-red-400 mb-3" role="alert">
          {msg}
        </p>
      )}

      <div className="flex flex-col gap-3">
        <input
          type="email"
          placeholder="Email"
          aria-label="Email"
          onChange={(e) => setForm({ ...form, email: e.target.value })}
          className={inputClass}
        />
        <input
          type="password"
          placeholder="Password"
          aria-label="Password"
          onChange={(e) => setForm({ ...form, password: e.target.value })}
          className={inputClass}
        />
        <GlassButton variant="primary" className="w-full mt-1" onClick={login}>
          Login
        </GlassButton>
      </div>
    </AuthShell>
  );
}
