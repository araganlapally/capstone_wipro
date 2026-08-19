// Maps a dynamic AI-generated exercise name (e.g. "Bench Press",
// "Cable Fly", "Squat") to a broad muscle-group category, so we can
// show a relevant illustration even though the exact exercise list
// is generated dynamically by the backend and can't be predicted.
//
// This is intentionally a best-effort keyword match, not an exhaustive
// exercise database — it degrades gracefully to "chest" if nothing
// matches, which is a safe default given most plans include a primary
// lift first.

const CATEGORY_KEYWORDS = {
  chest: ["bench", "chest", "press", "fly", "flye", "pec", "push-up", "pushup", "dip"],
  back: ["row", "pulldown", "pull-up", "pullup", "lat", "deadlift", "back", "shrug"],
  legs: ["squat", "leg", "lunge", "calf", "hamstring", "quad", "glute", "hip thrust"],
  core: ["plank", "crunch", "core", "ab ", "abs", "sit-up", "situp", "russian twist"],
  arms: ["curl", "tricep", "bicep", "pushdown", "extension", "arm"],
  shoulders: ["shoulder", "overhead", "military", "lateral raise", "delt", "arnold"],
};

export function getExerciseCategory(name = "") {
  const lower = name.toLowerCase();

  for (const [category, keywords] of Object.entries(CATEGORY_KEYWORDS)) {
    if (keywords.some((kw) => lower.includes(kw))) {
      return category;
    }
  }

  return "chest";
}
