import { Plus } from "lucide-react";
import { Button } from "@/components/ui/button";

export function AddPlantButton() {
  return (
    <Button
      type="button"
      aria-label="Add plant"
      size="icon-lg"
      className="fixed right-6 bottom-6 z-20 rounded-lg bg-neutral-900 text-white shadow-lg hover:bg-neutral-700"
    >
      <Plus aria-hidden="true" className="size-5" />
    </Button>
  );
}
