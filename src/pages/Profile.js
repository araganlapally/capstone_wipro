import React, { useEffect, useState } from "react";

export default function Profile() {
  const [profile, setProfile] = useState(null);

  useEffect(() => {
    loadProfile();
  }, []);

  const loadProfile = async () => {
    try {
      const user = JSON.parse(localStorage.getItem("user"));

      const response = await fetch(
        `http://localhost:8081/api/users/${user.id}/profile`,
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`
          }
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
      <div
        style={{
          minHeight: "100vh",
          background: "#050b12",
          color: "white",
          display: "flex",
          justifyContent: "center",
          alignItems: "center"
        }}
      >
        Loading Profile...
      </div>
    );
  }

  return (
    <div
      style={{
        minHeight: "100vh",
        background: "#050b12",
        color: "white",
        padding: "40px"
      }}
    >
      <h1 style={{ color: "#22e68a", marginBottom: "30px" }}>
        👤 My Profile
      </h1>

      <div
        style={{
          maxWidth: "800px",
          background: "#111827",
          padding: "30px",
          borderRadius: "20px",
          border: "1px solid #1f2937"
        }}
      >
        <p><b>Full Name:</b> {profile.user?.fullName}</p>
        <p><b>Email:</b> {profile.user?.email}</p>
        <p><b>Age:</b> {profile.age}</p>
        <p><b>Gender:</b> {profile.gender}</p>
        <p><b>Height:</b> {profile.height} cm</p>
        <p><b>Weight:</b> {profile.weight} kg</p>
        <p><b>Goal:</b> {profile.goal}</p>
      </div>
    </div>
  );
}