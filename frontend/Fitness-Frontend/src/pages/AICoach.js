import React, { useState } from "react";
import { Sparkles, Send } from "lucide-react";
import AppShell from "../components/app/AppShell";
import GlassCard from "../components/landing/GlassCard";
import GlassButton from "../components/landing/GlassButton";

/**
 * AICoach — dedicated AI Coach page.
 *
 * This is the same chat functionality that previously lived inline
 * inside Dashboard.js (same endpoint, same request/response shape,
 * same localStorage user lookup). Only the presentation moved to its
 * own route/page; no backend behavior changed.
 */
export default function AICoach() {
  const [question, setQuestion] = useState("");
  const [messages, setMessages] = useState([
    { type: "bot", text: "Hello! Ask me anything 💪" },
  ]);
  const [sending, setSending] = useState(false);

  const sendMessage = async () => {
    if (!question.trim()) return;

    const userQuestion = question;

    setMessages((prev) => [...prev, { type: "user", text: userQuestion }]);
    setQuestion("");
    setSending(true);

    try {
      const res = await fetch("http://localhost:8082/ai/ask", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          userId: JSON.parse(localStorage.getItem("user")).id,
          question: userQuestion,
        }),
      });

      const data = await res.json();

      setMessages((prev) => [...prev, { type: "bot", text: data.answer }]);
    } catch (err) {
      setMessages((prev) => [
        ...prev,
        { type: "bot", text: "Error connecting to AI" },
      ]);
    } finally {
      setSending(false);
    }
  };

  return (
    <AppShell>
      <div className="px-5 sm:px-8 py-8 max-w-3xl mx-auto">
        <h1 className="text-2xl font-semibold flex items-center gap-2 mb-1">
          <Sparkles size={22} className="text-[#4DFFB2]" aria-hidden="true" />
          AI Coach
          <span className="text-[10px] uppercase tracking-wide text-[#4DFFB2] border border-[#4DFFB2]/30 rounded px-1.5 py-0.5 ml-1">
            Beta
          </span>
        </h1>
        <p className="text-white/50 text-sm mb-8">
          Ask about your workouts, nutrition, or how to reach your goals.
        </p>

        <GlassCard strong glow className="p-6 flex flex-col" hover={false}>
          <div className="h-[420px] overflow-y-auto bg-black/20 rounded-xl border border-white/10 p-4 mb-4 flex flex-col gap-3">
            {messages.map((msg, index) => (
              <p
                key={index}
                className={[
                  "whitespace-pre-line rounded-xl px-4 py-2.5 text-sm max-w-[85%]",
                  msg.type === "user"
                    ? "self-end bg-[#4DFFB2] text-[#03110B]"
                    : "self-start bg-white/8 text-white",
                ].join(" ")}
              >
                {msg.text}
              </p>
            ))}
            {sending && (
              <p className="self-start text-xs text-white/40 px-2">Thinking…</p>
            )}
          </div>

          <div className="flex gap-2">
            <input
              placeholder="Ask FitAI..."
              aria-label="Ask FitAI"
              value={question}
              onChange={(e) => setQuestion(e.target.value)}
              onKeyDown={(e) => e.key === "Enter" && sendMessage()}
              className="flex-1 px-4 py-3 rounded-xl bg-white/5 border border-white/10 text-sm text-white placeholder:text-white/35 focus:outline-none focus:border-[#4DFFB2]/50"
            />
            <GlassButton
              variant="primary"
              className="!px-4"
              onClick={sendMessage}
              aria-label="Send message"
            >
              <Send size={16} />
            </GlassButton>
          </div>
        </GlassCard>
      </div>
    </AppShell>
  );
}
