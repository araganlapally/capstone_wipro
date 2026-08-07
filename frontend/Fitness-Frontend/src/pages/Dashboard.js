import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "../App.css";

export default function Dashboard() {
  const navigate = useNavigate();
  const [user, setUser] = useState(null);

  const [question, setQuestion] = useState("");
  const [messages, setMessages] = useState([
    { type: "bot", text: "Hello! Ask me anything 💪" }
  ]);

  useEffect(() => {
    try {
      const stored = localStorage.getItem("user");
      if (stored && stored !== "undefined") {
        setUser(JSON.parse(stored));
      }
    } catch {
      setUser(null);
    }
  }, []);

  const logout = () => {
    localStorage.clear();
    navigate("/");
  };

  const sendMessage = async () => {
    if (!question.trim()) return;

    const userQuestion = question;

    setMessages(prev => [...prev, { type: "user", text: userQuestion }]);
    setQuestion("");

    try {
      const res = await fetch("http://localhost:8082/ai/ask", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({
        userId: JSON.parse(localStorage.getItem("user")).id,
        question: userQuestion
})
      });

      const data = await res.json();

      setMessages(prev => [
        ...prev,
        { type: "bot", text: data.answer }
      ]);
    } catch (err) {
      setMessages(prev => [
        ...prev,
        { type: "bot", text: "Error connecting to AI" }
      ]);
    }
  };

  const metricCard = {
    background: "linear-gradient(145deg, #111827, #0b111c)",
    border: "1px solid #1f2937",
    borderRadius: "16px",
    padding: "20px",
    flex: 1,
    color: "white",
    boxShadow: "0 10px 25px rgba(0,0,0,0.35)"
  };

  const panelCard = {
    background: "linear-gradient(145deg, #111827, #0b111c)",
    border: "1px solid #1f2937",
    borderRadius: "18px",
    padding: "22px",
    color: "white",
    boxShadow: "0 10px 25px rgba(0,0,0,0.35)"
  };

  return (
    <div style={{
      display: "flex",
      minHeight: "100vh",
      background: "#050b12",
      color: "white",
      fontFamily: "Inter, Arial, sans-serif"
    }}>

      {/* SIDEBAR */}
      <div style={{
        width: "250px",
        background: "#070d16",
        padding: "22px",
        borderRight: "1px solid #1f2937"
      }}>
        <h2 style={{
          color: "#22e68a",
          letterSpacing: "2px",
          marginBottom: "30px"
        }}>
          ⚡ FITAI
        </h2>

        <div
  style={{
    padding: "12px 14px",
    marginBottom: "8px",
    borderRadius: "10px",
    background: "rgba(34,230,138,0.15)",
    color: "#22e68a",
    cursor: "pointer"
  }}
>
  🏠 Dashboard
</div>

<div
  onClick={() => navigate("/goals")}
  style={{
    padding: "12px 14px",
    marginBottom: "8px",
    borderRadius: "10px",
    color: "#cbd5e1",
    cursor: "pointer"
  }}
>
  🎯 Goal Setting
</div>

<div
  onClick={() => navigate("/workouts")}
  style={{
    padding: "12px 14px",
    marginBottom: "8px",
    borderRadius: "10px",
    color: "#cbd5e1",
    cursor: "pointer"
  }}
>
  💪 Workouts
</div>

<div
  onClick={() => navigate("/nutrition")}
  style={{
    padding: "12px 14px",
    marginBottom: "8px",
    borderRadius: "10px",
    color: "#cbd5e1",
    cursor: "pointer"
  }}
>
  🥗 Nutrition
</div>

<div
  style={{
    padding: "12px 14px",
    marginBottom: "8px",
    borderRadius: "10px",
    color: "#cbd5e1",
    cursor: "pointer"
  }}
>
  📈 Progress
</div>

<div
  style={{
    padding: "12px 14px",
    marginBottom: "8px",
    borderRadius: "10px",
    color: "#cbd5e1",
    cursor: "pointer"
  }}
>
  🤖 AI Coach
</div>

        <div
  style={{
    marginTop: "40px",
    borderTop: "1px solid #1f2937",
    paddingTop: "20px"
  }}
>
  <div
    onClick={() => navigate("/profile")}
    style={{
      color: "#cbd5e1",
      marginBottom: "12px",
      cursor: "pointer"
    }}
  >
    👤 Profile
  </div>

  <div
    style={{
      color: "#cbd5e1",
      marginBottom: "12px",
      cursor: "pointer"
    }}
  >
    ⚙️ Settings
  </div>

  <div
    onClick={logout}
    style={{
      color: "#ff6b6b",
      cursor: "pointer"
    }}
  >
    🚪 Log out
  </div>
</div>
      </div>

      {/* MAIN */}
      <div style={{ flex: 1, padding: "24px 30px" }}>

        {/* HEADER */}
        <div style={{
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
          marginBottom: "22px"
        }}>
          <div>
            <h2 style={{ margin: 0 }}>
              Good Evening, {user?.fullName || user?.name || user?.email || "User"} 👋
            </h2>
            <p style={{ color: "#94a3b8", marginTop: "6px" }}>
              You’re crushing your goals. Keep it up!
            </p>
          </div>

          <button
            onClick={logout}
            style={{
              background: "#22e68a",
              border: "none",
              color: "#020617",
              padding: "12px 22px",
              borderRadius: "12px",
              fontWeight: "bold",
              cursor: "pointer"
            }}
          >
            Logout
          </button>
        </div>

        {/* METRIC CARDS */}
        <div style={{
          display: "grid",
          gridTemplateColumns: "repeat(4, 1fr)",
          gap: "18px",
          marginBottom: "22px"
        }}>
          <div style={metricCard}>
            <p style={{ color: "#94a3b8", margin: 0 }}>Calories Burned</p>
            <h2 style={{ margin: "10px 0 4px" }}>568 <span style={{ fontSize: "14px" }}>kcal</span></h2>
            <p style={{ color: "#22e68a", margin: 0 }}>↑ 12% vs yesterday</p>
          </div>

          <div style={metricCard}>
            <p style={{ color: "#94a3b8", margin: 0 }}>Weight Progress</p>
            <h2 style={{ margin: "10px 0 4px" }}>72.4 <span style={{ fontSize: "14px" }}>kg</span></h2>
            <p style={{ color: "#22e68a", margin: 0 }}>↓ 1.3 kg vs last week</p>
          </div>

          <div style={metricCard}>
            <p style={{ color: "#94a3b8", margin: 0 }}>Daily Steps</p>
            <h2 style={{ margin: "10px 0 4px" }}>8,432</h2>
            <p style={{ color: "#22e68a", margin: 0 }}>↑ 15% vs yesterday</p>
          </div>

          <div style={metricCard}>
            <p style={{ color: "#94a3b8", margin: 0 }}>AI Fitness Score</p>
            <h2 style={{ margin: "10px 0 4px" }}>86 <span style={{ fontSize: "14px" }}>/100</span></h2>
            <p style={{ color: "#22e68a", margin: 0 }}>Excellent</p>
          </div>
        </div>

        {/* MAIN CONTENT */}
        <div style={{
          display: "grid",
          gridTemplateColumns: "1.2fr 1.3fr 1fr",
          gap: "18px"
        }}>

          {/* AI COACH */}
          <div style={panelCard}>
            <h3 style={{ marginTop: 0 }}>
              AI Coach <span style={{
                fontSize: "11px",
                color: "#22e68a",
                border: "1px solid #22e68a",
                padding: "2px 6px",
                borderRadius: "6px"
              }}>BETA</span>
            </h3>

            <div style={{
              height: "230px",
              overflowY: "auto",
              background: "#050b12",
              borderRadius: "12px",
              padding: "12px",
              border: "1px solid #1f2937",
              marginBottom: "12px"
            }}>
              {messages.map((msg, index) => (
                <p
                  key={index}
                  style={{
                    whiteSpace: "pre-line",
                    background: msg.type === "user" ? "#22e68a" : "#111827",
                    color: msg.type === "user" ? "#020617" : "white",
                    padding: "10px",
                    borderRadius: "10px",
                    fontSize: "13px",
                    textAlign: msg.type === "user" ? "right" : "left"
                  }}
                >
                  {msg.text}
                </p>
              ))}
            </div>

            <input
              placeholder="Ask FitAI..."
              value={question}
              onChange={(e) => setQuestion(e.target.value)}
              style={{
                width: "100%",
                padding: "11px",
                borderRadius: "10px",
                border: "1px solid #334155",
                background: "#0b111c",
                color: "white",
                marginBottom: "10px"
              }}
            />

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
                cursor: "pointer"
              }}
            >
              Chat with Coach
            </button>
          </div>

          {/* WORKOUT CARD */}
          <div style={panelCard}>
            <h3 style={{ marginTop: 0 }}>Today’s Workout</h3>
            <p style={{ color: "#94a3b8" }}>Push Day – Chest & Triceps</p>

            <ul style={{ lineHeight: "2", color: "#e5e7eb" }}>
              <li>Bench Press — 4 × 10</li>
              <li>Incline Dumbbell Press — 4 × 10</li>
              <li>Cable Fly — 3 × 12</li>
              <li>Tricep Pushdown — 3 × 12</li>
            </ul>

            <button style={{
              width: "100%",
              padding: "12px",
              background: "#22e68a",
              border: "none",
              borderRadius: "10px",
              color: "#020617",
              fontWeight: "bold",
              cursor: "pointer"
            }}>
              ▶ Start Workout
            </button>
          </div>

          {/* NUTRITION */}
          <div style={panelCard}>
            <h3 style={{ marginTop: 0 }}>Nutrition Today</h3>

            <div style={{
              width: "150px",
              height: "150px",
              borderRadius: "50%",
              border: "18px solid #22e68a",
              display: "flex",
              justifyContent: "center",
              alignItems: "center",
              margin: "20px auto",
              fontSize: "22px",
              fontWeight: "bold"
            }}>
              1,842
            </div>

            <p>Protein <span style={{ float: "right" }}>128g / 150g</span></p>
            <p>Carbs <span style={{ float: "right" }}>185g / 250g</span></p>
            <p>Fat <span style={{ float: "right" }}>56g / 70g</span></p>
          </div>
        </div>

        {/* BOTTOM ROW */}
        <div style={{
          display: "grid",
          gridTemplateColumns: "1fr 1fr 1fr",
          gap: "18px",
          marginTop: "18px"
        }}>
          <div style={panelCard}>
            <h3>Weight Trend</h3>
            <h2>72.4 kg</h2>
            <p style={{ color: "#22e68a" }}>↓ 1.3 kg vs last week</p>
          </div>

          <div style={panelCard}>
            <h3>Weekly Calories</h3>
            <h2>Avg. 2,150 kcal</h2>
            <p style={{ color: "#94a3b8" }}>Mon Tue Wed Thu Fri Sat Sun</p>
          </div>

          <div style={panelCard}>
            <h3>Muscle Recovery</h3>
            <h2>78%</h2>
            <p style={{ color: "#22e68a" }}>Good Recovery</p>
          </div>
        </div>

      </div>
    </div>
  );
}