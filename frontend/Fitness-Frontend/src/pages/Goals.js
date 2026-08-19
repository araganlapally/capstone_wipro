import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Target } from "lucide-react";
import AppShell from "../components/app/AppShell";
import GlassCard from "../components/landing/GlassCard";
import GlassButton from "../components/landing/GlassButton";

export default function Goals() {
  const navigate = useNavigate();

  const [profile, setProfile] = useState({
    age: "",
    height: "",
    weight: "",
    gender: "",
    goal: "",
  });

  const user = JSON.parse(localStorage.getItem("user"));
  const token = localStorage.getItem("token");

  useEffect(() => {
    fetchProfile();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const fetchProfile = async () => {
    try {
      const res = await fetch(
        `http://localhost:8081/api/users/${user.id}/profile`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (res.ok) {
        const data = await res.json();

        setProfile({
          age: data.age || "",
          height: data.height || "",
          weight: data.weight || "",
          gender: data.gender || "",
          goal: data.goal || "",
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
            Authorization: `Bearer ${token}`,
          },
          body: JSON.stringify(profile),
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

  const inputClass =
    "w-full px-4 py-3 mt-1.5 rounded-xl bg-white/5 border border-white/10 text-white " +
    "focus:outline-none focus:border-[#4DFFB2]/50 focus:ring-1 focus:ring-[#4DFFB2]/30 transition-colors";

  return (
    <AppShell>
      <div className="px-5 sm:px-8 py-8 max-w-2xl mx-auto">
        <h1 className="text-2xl font-semibold flex items-center gap-2 mb-8">
          <Target size={22} className="text-[#4DFFB2]" aria-hidden="true" />
          Goal Setting
        </h1>

        <GlassCard strong className="p-7" hover={false}>
          <div className="mb-4">
            <label className="text-sm text-white/60">Age</label>
            <input
              type="number"
              value={profile.age}
              onChange={(e) => setProfile({ ...profile, age: e.target.value })}
              className={inputClass}
            />
          </div>

          <div className="mb-4">
            <label className="text-sm text-white/60">Height (cm)</label>
            <input
              type="number"
              value={profile.height}
              onChange={(e) => setProfile({ ...profile, height: e.target.value })}
              className={inputClass}
            />
          </div>

          <div className="mb-4">
            <label className="text-sm text-white/60">Weight (kg)</label>
            <input
              type="number"
              value={profile.weight}
              onChange={(e) => setProfile({ ...profile, weight: e.target.value })}
              className={inputClass}
            />
          </div>

          <div className="mb-4">
            <label className="text-sm text-white/60">Gender</label>
            <select
              value={profile.gender}
              onChange={(e) => setProfile({ ...profile, gender: e.target.value })}
              className={inputClass}
            >
              <option value="">Select Gender</option>
              <option value="Male">Male</option>
              <option value="Female">Female</option>
            </select>
          </div>

          <div className="mb-6">
            <label className="text-sm text-white/60">Goal</label>
            <select
              value={profile.goal}
              onChange={(e) => setProfile({ ...profile, goal: e.target.value })}
              className={inputClass}
            >
              <option value="">Select Goal</option>
              <option value="Weight Loss">Weight Loss</option>
              <option value="Weight Gain">Weight Gain</option>
              <option value="Muscle Building">Muscle Building</option>
              <option value="General Fitness">General Fitness</option>
              <option value="Body Strengthening">Body Strengthening</option>
            </select>
          </div>

          <GlassButton variant="primary" className="w-full" onClick={saveProfile}>
            Save Goals
          </GlassButton>
        </GlassCard>
      </div>
    </AppShell>
  );
}
