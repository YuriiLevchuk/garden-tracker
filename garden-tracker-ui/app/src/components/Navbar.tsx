import { useEffect, useRef, useState } from "react";
import { ChevronDown, Settings, Sprout } from "lucide-react";
import { Link } from "react-router-dom";

const features = [
  { name: "Garden Managing", description: "abcd" },
  { name: "Plant Tracking", description: "abcd" },
  { name: "Task Calendar", description: "abcd" },
];

type NavbarProps = {
  auth?: boolean;
};

export function Navbar({ auth = false }: NavbarProps) {
  const [featuresOpen, setFeaturesOpen] = useState(false);
  const menuRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    function closeMenu(event: MouseEvent) {
      if (!menuRef.current?.contains(event.target as Node)) {
        setFeaturesOpen(false);
      }
    }

    function handleKeyDown(event: KeyboardEvent) {
      if (event.key === "Escape") {
        setFeaturesOpen(false);
      }
    }

    document.addEventListener("mousedown", closeMenu);
    document.addEventListener("keydown", handleKeyDown);

    return () => {
      document.removeEventListener("mousedown", closeMenu);
      document.removeEventListener("keydown", handleKeyDown);
    };
  }, []);

  return (
    <header className="relative z-10 bg-white shadow-sm">
      <nav
        aria-label="Main navigation"
        className="flex h-16 items-center justify-between px-8"
      >
        <Link to="/" className="flex items-center gap-2.5 text-base font-bold text-black">
          <Sprout aria-hidden="true" className="size-7 fill-black stroke-black" />
          <span>GARDEN TRACKER</span>
        </Link>

        <div className="flex items-center gap-1.5 text-sm font-medium text-black">
          <div ref={menuRef} className="relative">
            <button
              type="button"
              aria-expanded={featuresOpen}
              aria-controls="features-menu"
              onClick={() => setFeaturesOpen((isOpen) => !isOpen)}
              className="flex h-9 items-center gap-1 rounded-md bg-neutral-100 px-4 transition-colors hover:bg-neutral-200 focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-green-700"
            >
              Features
              <ChevronDown
                aria-hidden="true"
                className={`size-3.5 transition-transform ${featuresOpen ? "rotate-180" : ""}`}
              />
            </button>

            {featuresOpen && (
              <div
                id="features-menu"
                className="absolute right-0 top-11 w-72 rounded-b-lg bg-white py-2 shadow-lg"
              >
                {features.map((feature) => (
                  <Link
                    key={feature.name}
                    to={`/#${feature.name.toLowerCase().replaceAll(" ", "-")}`}
                    className="block px-4 py-2 transition-colors hover:bg-neutral-50 focus-visible:bg-neutral-50 focus-visible:outline-none"
                  >
                    <span className="block text-sm font-medium text-neutral-900">{feature.name}</span>
                    <span className="block text-xs text-neutral-500">{feature.description}</span>
                  </Link>
                ))}
              </div>
            )}
          </div>

          <Link
            to="/#about"
            className="flex h-9 items-center gap-1 rounded-md px-3 transition-colors hover:bg-neutral-100 focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-green-700"
          >
            About
            <ChevronDown aria-hidden="true" className="size-3.5" />
          </Link>
          <Link
            to={auth ? "/" : "/register"}
            className="flex h-9 items-center rounded-md bg-green-700 px-4 text-white transition-colors hover:bg-green-800 focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-green-700"
          >
            {auth ? "Go Back" : "Get Started!"}
          </Link>
          {!auth && (
            <button
              type="button"
              aria-label="Open settings"
              className="ml-8 grid size-9 place-items-center rounded-md transition-colors hover:bg-neutral-100 focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-green-700"
            >
              <Settings aria-hidden="true" className="size-6" />
            </button>
          )}
        </div>
      </nav>
    </header>
  );
}
