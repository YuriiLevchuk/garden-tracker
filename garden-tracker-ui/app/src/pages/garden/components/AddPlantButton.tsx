import { Plus } from "lucide-react";
import { Button } from "@/components/ui/button";

export function AddPlantButton() {
  return (
    <Button
      type="button"
      aria-label="Add plant"
      size="icon-lg"
      className="fixed right-4 bottom-4 z-20 rounded-lg bg-neutral-900 text-white shadow-lg hover:bg-neutral-700 sm:right-6 sm:bottom-6"
    >
      <Plus aria-hidden="true" className="size-5" />
    </Button>
  );
}
