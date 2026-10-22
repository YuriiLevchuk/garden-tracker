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

const tasks = [
  { name: "Water", status: "Done", date: "Oct 12, 2026", color: "bg-green-700" },
  { name: "Cut", status: "Due", date: "Oct 30, 2026", color: "bg-yellow-500" },
  { name: "Water", status: "Done", date: "Oct 12, 2026", color: "bg-green-700" },
  { name: "Water", status: "Done", date: "Oct 12, 2026", color: "bg-green-700" },
  { name: "Water", status: "Done", date: "Oct 12, 2026", color: "bg-green-700" },
  { name: "Water", status: "Done", date: "Oct 12, 2026", color: "bg-green-700" },
];

export function FlowerDetailsDialog() {
  return (
    <DialogContent className="max-w-4xl gap-0 overflow-hidden p-0 sm:max-w-4xl">
      <DialogHeader className="p-6 pr-14">
        <DialogTitle className="text-xl">Flower 2</DialogTitle>
        <DialogDescription>Marigold</DialogDescription>
      </DialogHeader>

      <div className="grid gap-6 border-t border-border p-6 md:grid-cols-[minmax(0,1fr)_minmax(0,1.25fr)]">
        <section aria-labelledby="tasks-heading" className="overflow-hidden rounded-lg border border-border">
          <h2 id="tasks-heading" className="border-b border-border px-4 py-3 text-sm font-semibold">
            Tasks
          </h2>
          <ScrollArea className="h-56">
            <ItemGroup className="gap-0 p-1">
              {tasks.map((task, index) => (
                <Item key={`${task.name}-${index}`} size="sm" className="rounded-md px-3 py-2">
                  <ItemContent>
                    <ItemTitle className="text-xs">{task.name}</ItemTitle>
                    <ItemDescription className="text-xs">
                      {task.status} ({task.date})
                    </ItemDescription>
                  </ItemContent>
                  <ItemActions>
                    <span aria-hidden="true" className={`size-2.5 rounded-full ${task.color}`} />
                  </ItemActions>
                </Item>
              ))}
            </ItemGroup>
          </ScrollArea>
        </section>

        <img
          src="https://images.unsplash.com/photo-1497250681960-ef046c08a56e?auto=format&fit=crop&w=1000&q=85"
          alt="Orange marigold flowers"
          className="aspect-square w-full rounded-lg border border-border object-cover"
        />
      </div>
    </DialogContent>
  );
}
