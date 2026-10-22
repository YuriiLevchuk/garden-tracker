import { ChevronDown, Settings } from "lucide-react";
import { Link } from "react-router-dom";
import { Button } from "@/components/ui/button";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { SidebarTrigger } from "@/components/ui/sidebar";
import logo from "@/resources/gt-logo.png";

const features = [
  { name: "Garden Managing", description: "abcd" },
  { name: "Plant Tracking", description: "abcd" },
  { name: "Task Calendar", description: "abcd" },
];

type NavbarProps = {
  auth?: boolean;
  dashboard?: boolean;
};

export function Navbar({ auth = false, dashboard = false }: NavbarProps) {
  const action = dashboard
    ? { label: "Log Out", to: "/" }
    : auth
      ? { label: "Go Back", to: "/" }
      : { label: "Get Started!", to: "/register" };

  return (
    <header className="relative z-10 bg-white shadow-sm">
      <nav aria-label="Main navigation" className="flex h-16 items-center justify-between px-8">
        <div className="flex items-center gap-2">
          {dashboard && <SidebarTrigger />}
          <Link to="/" className="flex items-center gap-2.5 text-2xl font-bold text-black">
            <img src={logo} alt="" className="size-10 object-contain" />
            <span>GARDEN TRACKER</span>
          </Link>
        </div>

        <div className="flex items-center gap-1.5 text-sm font-medium text-black">
          <DropdownMenu>
            <DropdownMenuTrigger
              render={
                <Button variant="ghost" size="lg">
                  Features
                  <ChevronDown aria-hidden="true" className="size-3.5" />
                </Button>
              }
            />
            <DropdownMenuContent align="end" className="w-72 p-2">
              {features.map((feature) => (
                <DropdownMenuItem
                  key={feature.name}
                  render={<Link to={`/#${feature.name.toLowerCase().replaceAll(" ", "-")}`} />}
                  className="flex-col items-start gap-0 px-3 py-2"
                >
                  <span className="text-sm font-medium text-neutral-900">{feature.name}</span>
                  <span className="text-xs text-neutral-500">{feature.description}</span>
                </DropdownMenuItem>
              ))}
            </DropdownMenuContent>
          </DropdownMenu>
          <Button variant="ghost" size="lg" render={<Link to="/#top" />}>
            About
            <ChevronDown aria-hidden="true" className="size-3.5" />
          </Button>
          <Button
            size="lg"
            render={<Link to={action.to} />}
            className="bg-green-700 text-white hover:bg-green-800"
          >
            {action.label}
          </Button>
          {!auth && !dashboard && (
            <Button
              type="button"
              aria-label="Open settings"
              variant="ghost"
              size="icon-lg"
              className="ml-8"
            >
              <Settings aria-hidden="true" className="size-6" />
            </Button>
          )}
        </div>
      </nav>
    </header>
  );
}
