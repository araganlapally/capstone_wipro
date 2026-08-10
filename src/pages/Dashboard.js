import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "../App.css";

export default function Dashboard() {
  const navigate = useNavigate();

  const [user, setUser] = useState(null);
  const [question, setQuestion] = useState("");

  const [messages, setMessages] = useState([
    {
      type: "bot",
      text: "Hello! Ask me anything 💪",
    },
  ]);

  // =========================================================
  // DYNAMIC GREETING
  // =========================================================

  const getGreeting = () => {
    const hour = new Date().getHours();

    if (hour < 12) {
      return "Good Morning";
    }

    if (hour < 17) {
      return "Good Afternoon";
    }

    return "Good Evening";
  };

  // =========================================================
  // LOAD USER
  // =========================================================

  useEffect(() => {
    try {
      const stored = localStorage.getItem("user");

      if (stored && stored !== "undefined") {
        setUser(JSON.parse(stored));
      }
    } catch (error) {
      console.error("User data error:", error);
      setUser(null);
    }
  }, []);

  // =========================================================
  // LOGOUT
  // =========================================================

  const logout = () => {
    localStorage.clear();
    navigate("/");
  };

  // =========================================================
  // AI CHAT
  // =========================================================

  const sendMessage = async () => {
    if (!question.trim()) return;

    const userQuestion = question;

    setMessages((prev) => [
      ...prev,
      {
        type: "user",
        text: userQuestion,
      },
    ]);

    setQuestion("");

    try {
      const res = await fetch("http://localhost:8081/ai/ask", {
        method: "POST",
        headers: {
          "Content-Type": "text/plain",
        },
        body: userQuestion,
      });

      if (!res.ok) {
        throw new Error("AI request failed");
      }

      const data = await res.json();

      setMessages((prev) => [
        ...prev,
        {
          type: "bot",
          text: data.answer || "I couldn't generate an answer.",
        },
      ]);
    } catch (error) {
      console.error("AI Error:", error);

      setMessages((prev) => [
        ...prev,
        {
          type: "bot",
          text: "Error connecting to AI",
        },
      ]);
    }
  };

  // =========================================================
  // ENTER KEY FOR CHAT
  // =========================================================

  const handleKeyDown = (e) => {
    if (e.key === "Enter") {
      sendMessage();
    }
  };

  // =========================================================
  // CARD STYLES
  // =========================================================

  const metricCard = {
    background: "linear-gradient(145deg, #111827, #0b111c)",
    border: "1px solid #1f2937",
    borderRadius: "16px",
    padding: "20px",
    minWidth: 0,
    boxSizing: "border-box",
    color: "white",
    boxShadow: "0 10px 25px rgba(0,0,0,0.35)",
  };

  const panelCard = {
    background: "linear-gradient(145deg, #111827, #0b111c)",
    border: "1px solid #1f2937",
    borderRadius: "18px",
    padding: "22px",
    minWidth: 0,
    boxSizing: "border-box",
    color: "white",
    boxShadow: "0 10px 25px rgba(0,0,0,0.35)",
  };

  const sidebarItem = {
    padding: "12px 14px",
    marginBottom: "8px",
    borderRadius: "10px",
    cursor: "pointer",
    color: "#cbd5e1",
    transition: "0.2s",
  };

  // =========================================================
  // PAGE
  // =========================================================

  return (
    <div
      style={{
        display: "flex",
        width: "100%",
        height: "100vh",
        overflow: "hidden",
        background: "#050b12",
        color: "white",
        fontFamily: "Inter, Arial, sans-serif",
      }}
    >
      {/* ===================================================== */}
      {/* SIDEBAR - FIXED */}
      {/* ===================================================== */}

      <aside
        style={{
          width: "250px",
          minWidth: "250px",
          height: "100vh",
          boxSizing: "border-box",
          background: "#070d16",
          padding: "22px",
          borderRight: "1px solid #1f2937",

          // Sidebar stays fixed
          overflow: "hidden",

          flexShrink: 0,
        }}
      >
        {/* LOGO */}

        <h2
          style={{
            color: "#22e68a",
            letterSpacing: "2px",
            margin: "0 0 30px 0",
            fontSize: "22px",
          }}
        >
          ⚡ FITAI
        </h2>

        {/* DASHBOARD */}

        <div
          style={{
            ...sidebarItem,
            background: "rgba(34,230,138,0.15)",
            color: "#22e68a",
          }}
        >
          🏠 Dashboard
        </div>

        {/* WORKOUT */}

        <div style={sidebarItem}>💪 Workout</div>

        {/* PROGRESS */}

        <div style={sidebarItem}>📊 Progress</div>

        {/* NUTRITION */}

        <div style={sidebarItem}>🥗 Nutrition</div>

        {/* AI COACH */}

        <div style={sidebarItem}>🤖 AI Coach</div>

        {/* ACCOUNT */}

        <div
          style={{
            borderTop: "1px solid #1f2937",
            marginTop: "30px",
            paddingTop: "20px",
          }}
        >
          <p
            style={{
              color: "#64748b",
              fontSize: "12px",
              marginBottom: "12px",
            }}
          >
            ACCOUNT
          </p>

          <div style={sidebarItem}>⚙️ Settings</div>

          <div
            style={{
              ...sidebarItem,
              color: "#f87171",
            }}
            onClick={logout}
          >
            🚪 Logout
          </div>
        </div>
      </aside>

      {/* ===================================================== */}
      {/* MAIN PAGE - SCROLLABLE */}
      {/* ===================================================== */}

      <main
        style={{
          flex: 1,
          minWidth: 0,
          height: "100vh",
          boxSizing: "border-box",

          padding: "24px 30px",

          // Only main page scrolls
          overflowY: "auto",
          overflowX: "hidden",

          scrollbarWidth: "thin",
          scrollbarColor: "#334155 #050b12",
        }}
      >
        {/* =================================================== */}
        {/* HEADER */}
        {/* =================================================== */}

        <div
          style={{
            display: "flex",
            justifyContent: "space-between",
            alignItems: "center",
            gap: "20px",
            marginBottom: "22px",
          }}
        >
          <div>
            <h2
              style={{
                margin: 0,
                fontSize: "25px",
              }}
            >
              {getGreeting()},{" "}
              {user?.fullName ||
                user?.name ||
                user?.email ||
                "User"}{" "}
              👋
            </h2>

            <p
              style={{
                color: "#94a3b8",
                marginTop: "6px",
                marginBottom: 0,
              }}
            >
              You're crushing your goals. Keep it up!
            </p>
          </div>

          {/* LOGOUT BUTTON */}

          <button
            onClick={logout}
            style={{
              background: "#22e68a",
              border: "none",
              color: "#020617",
              padding: "12px 22px",
              borderRadius: "12px",
              fontWeight: "bold",
              cursor: "pointer",
              whiteSpace: "nowrap",
            }}
          >
            Logout
          </button>
        </div>

        {/* =================================================== */}
        {/* METRIC CARDS */}
        {/* =================================================== */}

        <div
          style={{
            display: "grid",
            gridTemplateColumns: "repeat(4, minmax(0, 1fr))",
            gap: "18px",
            marginBottom: "22px",
          }}
        >
          {/* CALORIES */}

          <div style={metricCard}>
            <p
              style={{
                color: "#94a3b8",
                margin: 0,
                fontSize: "14px",
              }}
            >
              Calories Burned
            </p>

            <h2
              style={{
                margin: "10px 0 4px",
              }}
            >
              568{" "}
              <span
                style={{
                  fontSize: "14px",
                  color: "#94a3b8",
                }}
              >
                kcal
              </span>
            </h2>

            <p
              style={{
                color: "#22e68a",
                margin: 0,
                fontSize: "13px",
              }}
            >
              ↑ 12% vs yesterday
            </p>
          </div>

          {/* WEIGHT */}

          <div style={metricCard}>
            <p
              style={{
                color: "#94a3b8",
                margin: 0,
                fontSize: "14px",
              }}
            >
              Weight Progress
            </p>

            <h2
              style={{
                margin: "10px 0 4px",
              }}
            >
              72.4{" "}
              <span
                style={{
                  fontSize: "14px",
                  color: "#94a3b8",
                }}
              >
                kg
              </span>
            </h2>

            <p
              style={{
                color: "#22e68a",
                margin: 0,
                fontSize: "13px",
              }}
            >
              ↓ 1.3 kg vs last week
            </p>
          </div>

          {/* STEPS */}

          <div style={metricCard}>
            <p
              style={{
                color: "#94a3b8",
                margin: 0,
                fontSize: "14px",
              }}
            >
              Daily Steps
            </p>

            <h2
              style={{
                margin: "10px 0 4px",
              }}
            >
              8,432
            </h2>

            <p
              style={{
                color: "#22e68a",
                margin: 0,
                fontSize: "13px",
              }}
            >
              ↑ 15% vs yesterday
            </p>
          </div>

          {/* FITNESS SCORE */}

          <div style={metricCard}>
            <p
              style={{
                color: "#94a3b8",
                margin: 0,
                fontSize: "14px",
              }}
            >
              AI Fitness Score
            </p>

            <h2
              style={{
                margin: "10px 0 4px",
              }}
            >
              86{" "}
              <span
                style={{
                  fontSize: "14px",
                  color: "#94a3b8",
                }}
              >
                /100
              </span>
            </h2>

            <p
              style={{
                color: "#22e68a",
                margin: 0,
                fontSize: "13px",
              }}
            >
              Excellent
            </p>
          </div>
        </div>

        {/* =================================================== */}
        {/* MAIN CONTENT */}
        {/* =================================================== */}

        <div
          style={{
            display: "grid",
            gridTemplateColumns:
              "minmax(280px, 1.2fr) minmax(280px, 1.3fr) minmax(240px, 1fr)",
            gap: "18px",
            alignItems: "start",
          }}
        >
          {/* ================================================= */}
          {/* AI COACH */}
          {/* ================================================= */}

          <section style={panelCard}>
            <h3
              style={{
                marginTop: 0,
                display: "flex",
                alignItems: "center",
                gap: "8px",
              }}
            >
              AI Coach

              <span
                style={{
                  fontSize: "10px",
                  color: "#22e68a",
                  border: "1px solid #22e68a",
                  padding: "3px 7px",
                  borderRadius: "6px",
                  fontWeight: "normal",
                }}
              >
                BETA
              </span>
            </h3>

            {/* CHAT AREA */}

            <div
              style={{
                height: "230px",
                overflowY: "auto",
                background: "#050b12",
                borderRadius: "12px",
                padding: "12px",
                border: "1px solid #1f2937",
                marginBottom: "12px",
                boxSizing: "border-box",
              }}
            >
              {messages.map((msg, index) => (
                <div
                  key={index}
                  style={{
                    display: "flex",
                    justifyContent:
                      msg.type === "user"
                        ? "flex-end"
                        : "flex-start",
                    marginBottom: "10px",
                  }}
                >
                  <p
                    style={{
                      maxWidth: "85%",
                      whiteSpace: "pre-line",
                      background:
                        msg.type === "user"
                          ? "#22e68a"
                          : "#111827",
                      color:
                        msg.type === "user"
                          ? "#020617"
                          : "white",
                      padding: "10px",
                      borderRadius: "10px",
                      fontSize: "13px",
                      margin: 0,
                    }}
                  >
                    {msg.text}
                  </p>
                </div>
              ))}
            </div>

            {/* CHAT INPUT */}

            <input
              placeholder="Ask FitAI..."
              value={question}
              onChange={(e) => setQuestion(e.target.value)}
              onKeyDown={handleKeyDown}
              style={{
                width: "100%",
                boxSizing: "border-box",
                padding: "11px",
                borderRadius: "10px",
                border: "1px solid #334155",
                background: "#0b111c",
                color: "white",
                marginBottom: "10px",
                outline: "none",
              }}
            />

            {/* CHAT BUTTON */}

            <button
              onClick={sendMessage}
              style={{
                width: "100%",
                padding: "11px",
                borderRadius: "10px",
                border: "none",
                background: "#22e68a",
                color: "#020617",
                fontWeight: "bold",
                cursor: "pointer",
              }}
            >
              Chat with Coach
            </button>
          </section>

          {/* ================================================= */}
          {/* WORKOUT */}
          {/* ================================================= */}

          <section style={panelCard}>
            <div
              style={{
                display: "flex",
                justifyContent: "space-between",
                alignItems: "center",
                marginBottom: "15px",
                gap: "10px",
              }}
            >
              <h3 style={{ margin: 0 }}>
                Today's Workout
              </h3>

              <span
                style={{
                  color: "#22e68a",
                  fontSize: "12px",
                  whiteSpace: "nowrap",
                }}
              >
                Chest & Triceps
              </span>
            </div>

            {/* WORKOUT IMAGES */}

            <div
              style={{
                display: "grid",
                gridTemplateColumns: "1fr 1fr",
                gap: "12px",
                marginBottom: "15px",
              }}
            >
              {/* INCLINE DUMBBELL PRESS */}

              <div>
                <img
                  src="https://fitnessprogramer.com/wp-content/uploads/2021/02/Incline-Dumbbell-Press.gif"
                  alt="Incline Dumbbell Press"
                  style={{
                    width: "100%",
                    height: "130px",
                    objectFit: "cover",
                    borderRadius: "12px",
                    display: "block",
                  }}
                />

                <p
                  style={{
                    color: "#94a3b8",
                    textAlign: "center",
                    fontSize: "13px",
                    margin: "8px 0 0",
                  }}
                >
                  Incline Dumbbell Press
                </p>
              </div>

              {/* CABLE FLY */}

              <div>
                <img
                  src="https://fitnessprogramer.com/wp-content/uploads/2021/02/Cable-Crossover.gif"
                  alt="Cable Fly"
                  style={{
                    width: "100%",
                    height: "130px",
                    objectFit: "cover",
                    borderRadius: "12px",
                    display: "block",
                  }}
                />

                <p
                  style={{
                    color: "#94a3b8",
                    textAlign: "center",
                    fontSize: "13px",
                    margin: "8px 0 0",
                  }}
                >
                  Cable Fly
                </p>
              </div>
            </div>

            {/* EXERCISE LIST */}

            <div
              style={{
                background: "#050b12",
                border: "1px solid #1f2937",
                borderRadius: "12px",
                padding: "14px",
              }}
            >
              <p
                style={{
                  marginTop: 0,
                  color: "#94a3b8",
                  fontSize: "12px",
                }}
              >
                EXERCISES
              </p>

              <ul
                style={{
                  paddingLeft: "20px",
                  marginBottom: 0,
                  lineHeight: "2",
                  fontSize: "14px",
                }}
              >
                <li>Bench Press — 4 × 10</li>
                <li>Incline Dumbbell Press — 4 × 10</li>
                <li>Cable Fly — 3 × 12</li>
                <li>Tricep Pushdown — 3 × 12</li>
              </ul>
            </div>

            {/* START WORKOUT */}

            <a
              href="https://www.youtube.com/watch?v=W5_FtITGzl0"
              target="_blank"
              rel="noopener noreferrer"
              style={{
                display: "block",
                width: "100%",
                padding: "12px",
                background: "#22e68a",
                borderRadius: "10px",
                color: "#020617",
                fontWeight: "bold",
                textAlign: "center",
                textDecoration: "none",
                marginTop: "15px",
                boxSizing: "border-box",
              }}
            >
              ▶ Start Workout
            </a>
          </section>

          {/* ================================================= */}
          {/* NUTRITION */}
          {/* ================================================= */}

          <section style={panelCard}>
            <h3 style={{ marginTop: 0 }}>
              Nutrition
            </h3>

            <p
              style={{
                color: "#94a3b8",
                fontSize: "13px",
              }}
            >
              Daily calorie goal
            </p>

            {/* CALORIE CIRCLE */}

            <div
              style={{
                width: "150px",
                height: "150px",
                borderRadius: "50%",
                border: "18px solid #22e68a",
                display: "flex",
                justifyContent: "center",
                alignItems: "center",
                margin: "20px auto",
                fontSize: "22px",
                fontWeight: "bold",
                boxSizing: "border-box",
              }}
            >
              1,842
            </div>

            {/* PROTEIN */}

            <p>
              Protein

              <span
                style={{
                  float: "right",
                  color: "#94a3b8",
                }}
              >
                128g / 150g
              </span>
            </p>

            <div
              style={{
                height: "6px",
                background: "#1f2937",
                borderRadius: "10px",
                overflow: "hidden",
                marginBottom: "15px",
              }}
            >
              <div
                style={{
                  width: "85%",
                  height: "100%",
                  background: "#22e68a",
                }}
              />
            </div>

            {/* CARBS */}

            <p>
              Carbs

              <span
                style={{
                  float: "right",
                  color: "#94a3b8",
                }}
              >
                185g / 250g
              </span>
            </p>

            <div
              style={{
                height: "6px",
                background: "#1f2937",
                borderRadius: "10px",
                overflow: "hidden",
                marginBottom: "15px",
              }}
            >
              <div
                style={{
                  width: "74%",
                  height: "100%",
                  background: "#3b82f6",
                }}
              />
            </div>

            {/* FAT */}

            <p>
              Fat

              <span
                style={{
                  float: "right",
                  color: "#94a3b8",
                }}
              >
                56g / 70g
              </span>
            </p>

            <div
              style={{
                height: "6px",
                background: "#1f2937",
                borderRadius: "10px",
                overflow: "hidden",
              }}
            >
              <div
                style={{
                  width: "80%",
                  height: "100%",
                  background: "#f59e0b",
                }}
              />
            </div>
          </section>
        </div>

        {/* =================================================== */}
        {/* BOTTOM ROW */}
        {/* =================================================== */}

        <div
          style={{
            display: "grid",
            gridTemplateColumns: "repeat(3, minmax(0, 1fr))",
            gap: "18px",
            marginTop: "18px",
            paddingBottom: "30px",
          }}
        >
          {/* ================================================= */}
          {/* WEIGHT TREND */}
          {/* ================================================= */}

          <section style={panelCard}>
            <h3 style={{ marginTop: 0 }}>
              Weight Trend
            </h3>

            <h2
              style={{
                margin: "15px 0 5px",
              }}
            >
              72.4 kg
            </h2>

            <p
              style={{
                color: "#22e68a",
                margin: 0,
              }}
            >
              ↓ 1.3 kg vs last week
            </p>

            {/* SIMPLE CHART */}

            <div
              style={{
                height: "80px",
                marginTop: "20px",
                display: "flex",
                alignItems: "flex-end",
                gap: "8px",
              }}
            >
              {[55, 70, 62, 78, 60, 48, 40].map(
                (height, index) => (
                  <div
                    key={index}
                    style={{
                      flex: 1,
                      height: `${height}%`,
                      background: "#22e68a",
                      borderRadius: "5px 5px 0 0",
                      opacity: 0.7 + index * 0.04,
                    }}
                  />
                )
              )}
            </div>
          </section>

          {/* ================================================= */}
          {/* WEEKLY CALORIES */}
          {/* ================================================= */}

          <section style={panelCard}>
            <h3 style={{ marginTop: 0 }}>
              Weekly Calories
            </h3>

            <h2
              style={{
                margin: "15px 0 5px",
              }}
            >
              Avg. 2,150 kcal
            </h2>

            <p
              style={{
                color: "#94a3b8",
                fontSize: "13px",
              }}
            >
              Mon Tue Wed Thu Fri Sat Sun
            </p>

            <div
              style={{
                display: "flex",
                alignItems: "flex-end",
                height: "80px",
                gap: "8px",
                marginTop: "15px",
              }}
            >
              {[60, 75, 50, 85, 70, 90, 65].map(
                (height, index) => (
                  <div
                    key={index}
                    style={{
                      flex: 1,
                      height: `${height}%`,
                      background:
                        index === 5
                          ? "#22e68a"
                          : "#334155",
                      borderRadius:
                        "5px 5px 0 0",
                    }}
                  />
                )
              )}
            </div>
          </section>

          {/* ================================================= */}
          {/* MUSCLE RECOVERY */}
          {/* ================================================= */}

          <section style={panelCard}>
            <h3 style={{ marginTop: 0 }}>
              Muscle Recovery
            </h3>

            <h2
              style={{
                margin: "15px 0 5px",
              }}
            >
              78%
            </h2>

            <p
              style={{
                color: "#22e68a",
                marginBottom: "15px",
              }}
            >
              Good Recovery
            </p>

            <div
              style={{
                height: "10px",
                width: "100%",
                background: "#1f2937",
                borderRadius: "10px",
                overflow: "hidden",
              }}
            >
              <div
                style={{
                  width: "78%",
                  height: "100%",
                  background:
                    "linear-gradient(90deg, #22e68a, #16a34a)",
                  borderRadius: "10px",
                }}
              />
            </div>

            <p
              style={{
                color: "#64748b",
                fontSize: "12px",
                marginTop: "12px",
              }}
            >
              Your muscles are recovering well.
            </p>
          </section>
        </div>
      </main>
    </div>
  );
}