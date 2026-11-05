import {
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import {
  Item,
  ItemActions,
  ItemContent,
  ItemDescription,
  ItemGroup,
  ItemTitle,
} from "@/components/ui/item";
import { ScrollArea } from "@/components/ui/scroll-area";
import type { GardeningTask, Plant } from "@/lib/garden-api";

type FlowerDetailsDialogProps = {
  plant: Plant;
  tasks: GardeningTask[];
};

export function FlowerDetailsDialog({ plant, tasks }: FlowerDetailsDialogProps) {
  return (
    <DialogContent className="max-w-4xl gap-0 overflow-x-hidden p-0 sm:max-w-4xl">
      <DialogHeader className="p-5 pr-14 sm:p-6 sm:pr-14">
        <DialogTitle className="text-xl">{plant.plantType.name}</DialogTitle>
        <DialogDescription>{plant.plantType.name}</DialogDescription>
      </DialogHeader>

      <div className="grid gap-5 border-t border-border p-5 sm:p-6 md:grid-cols-[minmax(0,1fr)_minmax(0,1.25fr)] md:gap-6">
        <section aria-labelledby="tasks-heading" className="overflow-hidden rounded-lg border border-border">
          <h2 id="tasks-heading" className="border-b border-border px-4 py-3 text-sm font-semibold">Tasks</h2>
          <ScrollArea className="h-48 sm:h-56">
            <ItemGroup className="gap-0 p-1">
              {tasks.length === 0 ? (
                <p className="px-3 py-4 text-xs text-muted-foreground">No tasks scheduled for this plant.</p>
              ) : tasks.map((task) => {
                const isComplete = new Date(task.realizationTime) <= new Date();
                const date = new Intl.DateTimeFormat("en", { day: "numeric", month: "short", year: "numeric" }).format(new Date(task.realizationTime));

                return (
                  <Item key={task.id} size="sm" className="rounded-md px-3 py-2">
                    <ItemContent>
                      <ItemTitle className="text-xs">{task.gardeningTaskType.name}</ItemTitle>
                      <ItemDescription className="text-xs">{isComplete ? "Completed" : "Planned"} ({date})</ItemDescription>
                    </ItemContent>
                    <ItemActions><span aria-hidden="true" className={`size-2.5 rounded-full ${isComplete ? "bg-green-700" : "bg-yellow-500"}`} /></ItemActions>
                  </Item>
                );
              })}
            </ItemGroup>
          </ScrollArea>
        </section>

        <div className="aspect-square overflow-hidden rounded-lg border border-border bg-muted">
          <img
            src="https://images.unsplash.com/photo-1497250681960-ef046c08a56e?auto=format&fit=crop&w=1000&q=85"
            alt={`${plant.plantType.name} flowers`}
            className="size-full object-cover"
          />
        </div>
      </div>
    </DialogContent>
  );
}
