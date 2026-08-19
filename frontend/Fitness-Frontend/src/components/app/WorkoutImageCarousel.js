import React, { useState } from "react";
import { motion, AnimatePresence } from "framer-motion";
import { ChevronLeft, ChevronRight } from "lucide-react";
import { getExerciseCategory } from "../../utils/exerciseCategory";

import chestImg from "../../assets/workouts/chest.png";
import backImg from "../../assets/workouts/back.png";
import legsImg from "../../assets/workouts/legs.png";
import coreImg from "../../assets/workouts/core.png";
import armsImg from "../../assets/workouts/arms.png";
import shouldersImg from "../../assets/workouts/shoulders.png";

const CATEGORY_IMAGES = {
  chest: chestImg,
  back: backImg,
  legs: legsImg,
  core: coreImg,
  arms: armsImg,
  shoulders: shouldersImg,
};

/**
 * WorkoutImageCarousel — swipeable/clickable left-right carousel showing
 * one illustrated slide per exercise in today's workout. Each exercise
 * name (dynamic, from the AI-generated plan) is matched to a muscle-group
 * illustration via keyword matching (see utils/exerciseCategory.js), since
 * the backend does not provide per-exercise images.
 */
export default function WorkoutImageCarousel({ exercises = [] }) {
  const [index, setIndex] = useState(0);

  if (!exercises.length) return null;

  const total = exercises.length;
  const current = exercises[index];
  const category = getExerciseCategory(current?.name);
  const image = CATEGORY_IMAGES[category];

  const goPrev = () => setIndex((i) => (i - 1 + total) % total);
  const goNext = () => setIndex((i) => (i + 1) % total);

  return (
    <div className="relative">
      <div className="relative h-56 sm:h-64 rounded-2xl overflow-hidden bg-black/30 border border-white/10">
        <AnimatePresence mode="wait">
          <motion.img
            key={index}
            src={image}
            alt={current?.name || "Exercise illustration"}
            initial={{ opacity: 0, x: 24 }}
            animate={{ opacity: 1, x: 0 }}
            exit={{ opacity: 0, x: -24 }}
            transition={{ duration: 0.35, ease: "easeOut" }}
            className="w-full h-full object-cover"
          />
        </AnimatePresence>

        {/* Gradient overlay + exercise label */}
        <div className="absolute inset-x-0 bottom-0 bg-gradient-to-t from-black/85 to-transparent px-4 pt-10 pb-3">
          <p className="text-sm font-medium">{current?.name}</p>
          <p className="text-xs text-white/55">
            {current?.sets && current?.reps
              ? `${current.sets} × ${current.reps}`
              : ""}
          </p>
        </div>

        {/* Prev / Next controls */}
        {total > 1 && (
          <>
            <button
              onClick={goPrev}
              aria-label="Previous exercise"
              className="absolute left-2 top-1/2 -translate-y-1/2 w-8 h-8 rounded-full liquid-glass flex items-center justify-center hover:bg-white/15 transition-colors focus:outline-none focus-visible:ring-2 focus-visible:ring-[#4DFFB2]"
            >
              <ChevronLeft size={16} />
            </button>
            <button
              onClick={goNext}
              aria-label="Next exercise"
              className="absolute right-2 top-1/2 -translate-y-1/2 w-8 h-8 rounded-full liquid-glass flex items-center justify-center hover:bg-white/15 transition-colors focus:outline-none focus-visible:ring-2 focus-visible:ring-[#4DFFB2]"
            >
              <ChevronRight size={16} />
            </button>
          </>
        )}
      </div>

      {/* Dot indicators */}
      {total > 1 && (
        <div className="flex justify-center gap-1.5 mt-3">
          {exercises.map((_, i) => (
            <button
              key={i}
              onClick={() => setIndex(i)}
              aria-label={`Go to exercise ${i + 1}`}
              className={[
                "h-1.5 rounded-full transition-all",
                i === index ? "w-5 bg-[#4DFFB2]" : "w-1.5 bg-white/20",
              ].join(" ")}
            />
          ))}
        </div>
      )}
    </div>
  );
}
