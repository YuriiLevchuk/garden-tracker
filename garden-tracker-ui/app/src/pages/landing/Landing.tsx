import { Navbar } from "@/components/Navbar";

const features = [
  {
    title: "Manage your plants",
    description: "Keep track of everything growing in your garden.",
  },
  {
    title: "Plan your tasks",
    description: "Stay on top of watering and gardening schedules.",
  },
  {
    title: "Track your garden",
    description: "Organize your garden in one convenient place.",
  },
];

export function LandingPage() {
  return (
    <div id="top" className="min-h-screen bg-background text-foreground">
      <Navbar />
      <main>
        <section className="grid min-h-[calc(100vh-4rem)] border-b border-border bg-white md:grid-cols-2">
          <div className="flex flex-col justify-center px-8 py-16 sm:px-12 lg:px-16">
            <h1 className="max-w-md text-4xl font-bold tracking-tight text-foreground md:text-5xl">
              Grow a better garden,
              <span className="text-green-700"> effortlessly.</span>
            </h1>

            <p className="mt-6 max-w-md text-base leading-7 text-muted-foreground">
              Plan, organize, and track your garden&apos;s growth from one simple dashboard.
            </p>

            <ul className="mt-14 space-y-8">
              {features.map(({ title, description }) => (
                <li key={title} className="max-w-md">
                  <h2 className="flex items-center gap-2 text-base font-bold text-foreground">
                    <span aria-hidden="true" className="size-1.5 rounded-full bg-foreground" />
                    {title}
                  </h2>
                  <p className="mt-2 pl-3.5 text-sm leading-6 text-muted-foreground">{description}</p>
                </li>
              ))}
            </ul>
          </div>

          <div className="min-h-80 border-t border-border md:min-h-0 md:border-t-0 md:border-l">
            <img
              src="https://images.unsplash.com/photo-1585320806297-9794b3e4eeae?auto=format&fit=crop&w=1400&q=85"
              alt="A lush garden viewed from above"
              className="size-full object-cover"
            />
          </div>
        </section>
      </main>
    </div>
  );
}
