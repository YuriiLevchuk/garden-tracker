import { Leaf, Sprout, CalendarDays } from "lucide-react";
import { Card, CardContent } from "@/components/ui/card";

const features = [
  {
    title: "Manage your plants",
    description: "Keep track of everything growing in your garden.",
    icon: Sprout,
  },
  {
    title: "Plan your tasks",
    description: "Stay on top of watering and gardening schedules.",
    icon: CalendarDays,
  },
  {
    title: "Track your garden",
    description: "Organize your garden in one convenient place.",
    icon: Leaf,
  },
];

export function LandingPage() {
  return (
    <div className="min-h-screen bg-background text-foreground">
      <main>
        <section className="mx-auto max-w-7xl px-6 py-24 text-center">
          <h1 className="mx-auto max-w-3xl text-5xl font-bold tracking-tight md:text-6xl">
            Grow a better garden,
            <span className="text-green-600"> effortlessly.</span>
          </h1>

          <p className="mx-auto mt-6 max-w-xl text-lg text-muted-foreground">
            Plan, organize, and track your garden's growth
            from one simple dashboard.
          </p>
        </section>

        <section className="mx-auto grid max-w-7xl gap-6 px-6 pb-24 md:grid-cols-3">
          {features.map(({ title, description, icon: Icon }) => (
            <Card key={title}>
              <CardContent className="space-y-4 pt-6">
                <Icon className="size-8 text-green-600" />
                <h2 className="text-xl font-semibold">{title}</h2>
                <p className="text-muted-foreground">{description}</p>
              </CardContent>
            </Card>
          ))}
        </section>
      </main>
    </div>
  );
}