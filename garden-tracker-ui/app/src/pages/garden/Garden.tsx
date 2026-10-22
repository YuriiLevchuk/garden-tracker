import { UserRound } from "lucide-react";
import { Link } from "react-router-dom";
import { Navbar } from "@/components/Navbar";
import { Button } from "@/components/ui/button";
import {
  Sidebar,
  SidebarContent,
  SidebarGroup,
  SidebarGroupContent,
  SidebarGroupLabel,
  SidebarHeader,
  SidebarInset,
  SidebarMenu,
  SidebarMenuButton,
  SidebarMenuItem,
  SidebarProvider,
} from "@/components/ui/sidebar";
import { FlowerItem } from "@/pages/garden/components/FlowerItem";
import { AddPlantButton } from "@/pages/garden/components/AddPlantButton";

const gardens = ["Garden 1", "Garden 2", "Garden 3", "Garden 4", "Garden 5", "Garden 6", "Garden 7"];

export function GardenPage() {
  return (
    <SidebarProvider>
      <Sidebar>
        <SidebarHeader className="p-6">
          <div className="flex items-center gap-2 text-sm font-medium">
            <span className="grid size-8 place-items-center rounded-md bg-neutral-900 text-white">
              <UserRound aria-hidden="true" className="size-4" />
            </span>
            <span>
              <span className="block">User123</span>
              <Button variant="link" size="xs" render={<Link to="/" />} className="h-auto p-0 text-xs text-muted-foreground">
                Log out
              </Button>
            </span>
          </div>
        </SidebarHeader>

        <SidebarContent>
          <SidebarGroup>
            <SidebarGroupLabel>Gardens</SidebarGroupLabel>
            <SidebarGroupContent>
              <SidebarMenu>
                {gardens.map((garden) => (
                  <SidebarMenuItem key={garden}>
                    <SidebarMenuButton
                      isActive={garden === "Garden 4"}
                      className="data-active:bg-transparent"
                    >
                      <span>{garden}</span>
                    </SidebarMenuButton>
                  </SidebarMenuItem>
                ))}
              </SidebarMenu>
            </SidebarGroupContent>
          </SidebarGroup>
        </SidebarContent>
      </Sidebar>

      <SidebarInset className="bg-white">
        <Navbar dashboard />
        <section aria-label="Plants" className="grid gap-4 p-6 sm:grid-cols-2 sm:p-8">
          {Array.from({ length: 8 }, (_, index) => (
            <FlowerItem key={index} />
          ))}
        </section>
      </SidebarInset>

      <AddPlantButton />
    </SidebarProvider>
  );
}
