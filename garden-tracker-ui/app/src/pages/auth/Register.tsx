import { Link } from "react-router-dom";
import { Navbar } from "@/components/Navbar";
import { Input } from "@/components/ui/input";

const gardenImage = "https://images.unsplash.com/photo-1585320806297-9794b3e4eeae?auto=format&fit=crop&w=1400&q=85";

export function RegisterPage() {
  return (
    <div className="min-h-screen bg-background text-foreground">
      <Navbar auth />
      <main
        className="flex min-h-[calc(100vh-4rem)] justify-center bg-cover bg-center px-4 sm:px-8"
        style={{ backgroundImage: `url(${gardenImage})` }}
      >
        <section className="flex w-full max-w-2xl flex-col items-center bg-white px-8 py-16 sm:px-16">
          <h1 className="max-w-sm text-center text-4xl font-bold leading-none tracking-tight sm:text-5xl">
            Welcome to Garden Tracker!
          </h1>

          <form
            className="mt-12 w-full max-w-xs"
            onSubmit={(event) => event.preventDefault()}
          >
            <div className="space-y-4">
              <label className="block text-xs font-medium">
                Name
                <Input
                  name="name"
                  type="text"
                  placeholder="Jordan Lee"
                  autoComplete="name"
                  required
                  className="mt-1.5"
                />
              </label>
              <label className="block text-xs font-medium">
                Username
                <Input
                  name="username"
                  type="text"
                  placeholder="abcd"
                  autoComplete="username"
                  required
                  className="mt-1.5"
                />
              </label>
              <label className="block text-xs font-medium">
                Email
                <Input
                  name="email"
                  type="email"
                  placeholder="name@example.com"
                  autoComplete="email"
                  required
                  className="mt-1.5"
                />
              </label>
            </div>

            <div className="mt-4 flex gap-2">
              <button type="reset" className="h-8 rounded-md border border-border px-3 text-xs font-medium hover:bg-muted">
                Reset
              </button>
              <button type="submit" className="h-8 rounded-md bg-neutral-900 px-3 text-xs font-medium text-white hover:bg-neutral-700">
                Register
              </button>
            </div>
          </form>

          <div className="mt-16 w-full max-w-xs">
            <p className="text-xs font-medium">Already have an account?</p>
            <Link
              to="/login"
              className="mt-2 flex h-9 w-full items-center justify-center rounded-md bg-neutral-900 text-sm font-medium text-white hover:bg-neutral-700 focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-green-700"
            >
              Login
            </Link>
          </div>
        </section>
      </main>
    </div>
  );
}
