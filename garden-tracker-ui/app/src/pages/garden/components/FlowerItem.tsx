import { Flower2 } from "lucide-react";
import {
  Item,
  ItemActions,
  ItemContent,
  ItemDescription,
  ItemMedia,
  ItemTitle,
} from "@/components/ui/item";
import { Dialog, DialogTrigger } from "@/components/ui/dialog";
import type { GardeningTask, Plant } from "@/lib/garden-api";
import { FlowerDetailsDialog } from "@/pages/garden/components/FlowerDetailsDialog";

type FlowerItemProps = {
  plant: Plant;
  tasks: GardeningTask[];
};

export function FlowerItem({ plant, tasks }: FlowerItemProps) {
  const pendingTasks = tasks.filter((task) => new Date(task.realizationTime) > new Date()).length;

  return (
    <Dialog>
      <DialogTrigger
        render={<Item variant="outline" className="min-h-24 cursor-pointer bg-white px-5 py-4 shadow-xs transition-shadow hover:shadow-sm" />}
      >
        <ItemMedia variant="icon" className="!self-center !translate-y-0">
          <Flower2 aria-hidden="true" className="size-7 text-foreground" />
        </ItemMedia>
        <ItemContent>
          <ItemTitle>{plant.plantType.name}</ItemTitle>
          <ItemDescription className="text-xs">
            {pendingTasks === 0 ? "No pending tasks" : `${pendingTasks} pending ${pendingTasks === 1 ? "task" : "tasks"}`}
          </ItemDescription>
        </ItemContent>
        <ItemActions>
          <span aria-hidden="true" className={`size-3 rounded-full ${pendingTasks === 0 ? "bg-green-700" : "bg-yellow-500"}`} />
        </ItemActions>
      </DialogTrigger>
      <FlowerDetailsDialog plant={plant} tasks={tasks} />
    </Dialog>
  );
}
