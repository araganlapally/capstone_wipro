import React, { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import AuthShell from "../components/app/AuthShell";
import GlassButton from "../components/landing/GlassButton";

const API_BASE = "http://localhost:8081/api";

export default function Register() {
  const [form, setForm] = useState({ name: "", email: "", password: "" });
  const navigate = useNavigate();

  const register = async () => {
    try {
      await fetch(`${API_BASE}/auth/register`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          fullName: form.name,
          email: form.email,
          password: form.password,
        }),
      });

      navigate("/login");
    } catch {}
  };

  const inputClass =
    "w-full px-4 py-3 rounded-xl bg-white/5 border border-white/10 text-white " +
    "placeholder:text-white/35 focus:outline-none focus:border-[#4DFFB2]/50 " +
    "focus:ring-1 focus:ring-[#4DFFB2]/30 transition-colors";

  return (
    <AuthShell
      titleLines={["Get Started.", "Start Your Journey."]}
      subtitle="AI-powered fitness, built for you."
      footer={
        <p className="mt-5 text-sm text-white/55 text-center">
          Already have an account?{" "}
          <Link to="/login" className="text-[#4DFFB2] font-medium hover:underline">
            Login
          </Link>
        </p>
      }
    >
      <div className="flex flex-col gap-3">
        <input
          placeholder="Full Name"
          aria-label="Full Name"
          onChange={(e) => setForm({ ...form, name: e.target.value })}
          className={inputClass}
        />
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
        <GlassButton variant="primary" className="w-full mt-1" onClick={register}>
          Register
        </GlassButton>
      </div>
    </AuthShell>
  );
}
