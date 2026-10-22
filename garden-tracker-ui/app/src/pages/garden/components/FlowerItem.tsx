import { Flower2 } from "lucide-react";
import {
  Item,
  ItemActions,
  ItemContent,
  ItemDescription,
  ItemMedia,
  ItemTitle,
} from "@/components/ui/item";

export function FlowerItem() {
  return (
    <Item variant="outline" className="min-h-24 bg-white px-5 py-4 shadow-xs">
      <ItemMedia variant="icon" className="!self-center !translate-y-0">
        <Flower2 aria-hidden="true" className="size-7 text-foreground" />
      </ItemMedia>
      <ItemContent>
        <ItemTitle>Flower 1</ItemTitle>
        <ItemDescription className="text-xs">No pending tasks</ItemDescription>
      </ItemContent>
      <ItemActions>
        <span aria-hidden="true" className="size-3 rounded-full bg-green-700" />
      </ItemActions>
    </Item>
  );
}
