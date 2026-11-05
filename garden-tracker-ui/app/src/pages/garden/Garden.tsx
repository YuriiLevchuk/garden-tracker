import { UserRound } from "lucide-react";
import { Link, useOutletContext } from "react-router-dom";
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
import { logout } from "@/lib/auth-api";
import { getGardenDashboard, type User } from "@/lib/garden-api";
import { FlowerItem } from "@/pages/garden/components/FlowerItem";
import { AddPlantButton } from "@/pages/garden/components/AddPlantButton";

export function GardenPage() {
  const user = useOutletContext<User>();
  const dashboard = getGardenDashboard();
  const selectedGarden = dashboard.gardens[3];

  return (
    <SidebarProvider>
      <Sidebar>
        <SidebarHeader className="p-6">
          <div className="flex items-center gap-2 text-sm font-medium">
            <span className="grid size-8 place-items-center rounded-md bg-neutral-900 text-white">
              <UserRound aria-hidden="true" className="size-4" />
            </span>
            <span>
              <span className="block">{user.email}</span>
              <Button variant="link" size="xs" render={<Link to="/" onClick={logout} />} className="h-auto p-0 text-xs text-muted-foreground">
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
                {dashboard.gardens.map((garden) => (
                  <SidebarMenuItem key={garden.id}>
                    <SidebarMenuButton
                      isActive={garden.id === selectedGarden.id}
                      className="data-active:bg-transparent"
                    >
                      <span>{garden.name}</span>
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
        <section aria-label="Plants" className="grid gap-4 p-4 sm:grid-cols-2 sm:p-8 xl:grid-cols-3">
          {selectedGarden.plants.map((plant) => (
            <FlowerItem
              key={plant.id}
              plant={plant}
              tasks={selectedGarden.gardeningTasks.filter((task) => task.plants.some((taskPlant) => taskPlant.id === plant.id))}
            />
          ))}
        </section>
      </SidebarInset>

      <AddPlantButton />
    </SidebarProvider>
  );
}
