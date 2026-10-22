import { Link, useNavigate } from "react-router-dom";
import { Navbar } from "@/components/Navbar";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";

const gardenImage = "https://images.unsplash.com/photo-1585320806297-9794b3e4eeae?auto=format&fit=crop&w=1400&q=85";

export function LoginPage() {
  const navigate = useNavigate();

  return (
    <div className="min-h-screen bg-background text-foreground">
      <Navbar auth />
      <main
        className="flex min-h-[calc(100vh-4rem)] justify-center bg-cover bg-center px-4 sm:px-8"
        style={{ backgroundImage: `url(${gardenImage})` }}
      >
        <section className="flex w-full max-w-2xl flex-col items-center bg-white px-8 py-16 sm:px-16">
          <h1 className="max-w-sm text-center text-4xl font-bold leading-none tracking-tight sm:text-5xl">
            Welcome back to Garden Tracker!
          </h1>

          <form
            className="mt-12 w-full max-w-xs"
            onSubmit={(event) => {
              event.preventDefault();
              navigate("/garden");
            }}
            >
            <div className="space-y-4">
              <div>
                <Label htmlFor="username" className="text-xs">Username</Label>
                <Input
                  id="username"
                  name="username"
                  type="text"
                  placeholder="abcd"
                  autoComplete="username"
                  required
                  className="mt-1.5"
                />
              </div>
              <div>
                <Label htmlFor="password" className="text-xs">Password</Label>
                <Input
                  id="password"
                  name="password"
                  type="password"
                  placeholder="Enter your password"
                  autoComplete="current-password"
                  required
                  className="mt-1.5"
                />
              </div>
            </div>

            <div className="mt-4 flex gap-2">
              <Button type="reset" variant="outline">
                Reset
              </Button>
              <Button type="submit">
                Login
              </Button>
            </div>
          </form>

          <div className="mt-16 w-full max-w-xs">
            <p className="text-xs font-medium">Don&apos;t have an account?</p>
            <Button render={<Link to="/register" />} size="lg" className="mt-2 w-full">
              Register
            </Button>
          </div>
        </section>
      </main>
    </div>
  );
}
