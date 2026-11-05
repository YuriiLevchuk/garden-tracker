// These types mirror the backend entities until its REST contract is available.
export type User = {
  id: number;
  email: string;
};

export type Category = {
  id: number;
  name: string;
};

export type GardeningTaskType = {
  id: number;
  name: string;
  category: Category;
};

export type PlantType = {
  id: number;
  name: string;
};

export type Plant = {
  id: number;
  plantType: PlantType;
};

export type GardeningTask = {
  id: string;
  realizationTime: string;
  gardeningTaskType: GardeningTaskType;
  plants: Plant[];
};

export type Garden = {
  id: number;
  name: string;
  plants: Plant[];
  gardeningTasks: GardeningTask[];
};

export type GardenDashboard = {
  user: User;
  gardens: Garden[];
};

const user: User = { id: 1, email: "gardener@example.com" };
const watering: GardeningTaskType = { id: 1, name: "Water", category: { id: 1, name: "Care" } };
const pruning: GardeningTaskType = { id: 2, name: "Cut", category: { id: 1, name: "Care" } };
const marigold: Plant = { id: 1, plantType: { id: 1, name: "Marigold" } };
const basil: Plant = { id: 2, plantType: { id: 2, name: "Basil" } };

const gardens: Garden[] = Array.from({ length: 7 }, (_, index) => ({
  id: index + 1,
  name: `Garden ${index + 1}`,
  plants: index === 3 ? [marigold, basil] : [],
  gardeningTasks: index === 3
    ? [
        { id: "7f7ca7f4-4405-4d32-b62c-22cf4fdce703", realizationTime: "2026-10-12T09:00:00Z", gardeningTaskType: watering, plants: [marigold] },
        { id: "7e4e2ca8-4709-4658-b4c3-2d3323ca1f4c", realizationTime: "2026-10-30T09:00:00Z", gardeningTaskType: pruning, plants: [marigold] },
      ]
    : [],
}));

export function getGardenDashboard(): GardenDashboard {
  return { user, gardens };
}
