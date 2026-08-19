import React, { useEffect, useState } from "react";
import { User } from "lucide-react";
import AppShell from "../components/app/AppShell";
import GlassCard from "../components/landing/GlassCard";

export default function Profile() {
  const [profile, setProfile] = useState(null);

  useEffect(() => {
    loadProfile();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const loadProfile = async () => {
    try {
      const user = JSON.parse(localStorage.getItem("user"));

      const response = await fetch(
        `http://localhost:8081/api/users/${user.id}/profile`,
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        }
      );

      const data = await response.json();
      setProfile(data);
    } catch (error) {
      console.error(error);
    }
  };

  if (!profile) {
    return (
      <AppShell>
        <div className="min-h-[80vh] flex items-center justify-center text-white/60 text-sm">
          Loading Profile...
        </div>
      </AppShell>
    );
  }

  const FIELDS = [
    { label: "Full Name", value: profile.user?.fullName },
    { label: "Email", value: profile.user?.email },
    { label: "Age", value: profile.age },
    { label: "Gender", value: profile.gender },
    { label: "Height", value: profile.height ? `${profile.height} cm` : undefined },
    { label: "Weight", value: profile.weight ? `${profile.weight} kg` : undefined },
    { label: "Goal", value: profile.goal },
  ];

  return (
    <AppShell>
      <div className="px-5 sm:px-8 py-8 max-w-3xl mx-auto">
        <h1 className="text-2xl font-semibold flex items-center gap-2 mb-8">
          <User size={22} className="text-[#4DFFB2]" aria-hidden="true" />
          My Profile
        </h1>

        <GlassCard strong className="p-7 grid sm:grid-cols-2 gap-5" hover={false}>
          {FIELDS.map((f) => (
            <div key={f.label}>
              <p className="text-xs text-white/45 mb-1">{f.label}</p>
              <p className="text-base">{f.value ?? "—"}</p>
            </div>
          ))}
        </GlassCard>
      </div>
    </AppShell>
  );
}
