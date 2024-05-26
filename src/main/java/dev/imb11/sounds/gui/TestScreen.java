//package dev.imb11.sounds.gui;
//
//import dev.imb11.sounds.config.SoundsConfig;
//import dev.imb11.sounds.config.utils.ConfigGroup;
//import dev.isxander.yacl3.api.ConfigCategory;
//import net.minecraft.client.gui.DrawContext;
//import net.minecraft.client.gui.screen.Screen;
//import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
//import net.minecraft.client.gui.widget.ClickableWidget;
//import net.minecraft.text.Text;
//import org.jetbrains.annotations.Nullable;
//
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.concurrent.atomic.AtomicInteger;
//import java.util.function.Consumer;
//
//public class TestScreen extends Screen {
//
//    public ArrayList<CategoryEntryWidget> categories = new ArrayList<>();
//    public float scrollOffsetY = 0;
//    public final int categoryListX = 10, categoryListY = 30, categoryListWidth = 110;
//
//    public TestScreen() {
//        super(Text.of("Sounds Config"));
//    }
//
//    @Override
//    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
//        if(mouseX >= categoryListX && mouseX <= categoryListWidth + categoryListX && mouseY >= categoryListY && mouseY <= height - 10) {
//            float newOffset = (float) (scrollOffsetY + verticalAmount * 12.5f);
//            AtomicInteger maxY = new AtomicInteger(categoryListY);
//            for (CategoryEntryWidget category : categories) {
//                maxY.addAndGet(category.getHeight() + 5);
//                category.processSubcategories(subCategory -> maxY.addAndGet(subCategory.getHeight() + 5));
//            }
//            if(maxY.get() > height - 10) {
//                scrollOffsetY = Math.max(Math.min(10, newOffset), height - maxY.get() - 10);
//                scrollOffsetY = Math.min(0, scrollOffsetY);
//                redrawCategories(true);
//            }
//        }
//
//        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
//    }
//
//    @Override
//    protected void init() {
//        super.init();
//
//        if(categories.isEmpty()) {
//            ConfigGroup<?>[] groups = SoundsConfig.getAll();
//            int widgetHeight = 18, y = categoryListY;
//            for (ConfigGroup<?> group : groups) {
//                CategoryEntryWidget categoryElement = new CategoryEntryWidget(Text.of(group.getYACL().title()), categoryListX, y, categoryListWidth, widgetHeight);
//                y += widgetHeight + 5;
//                for (ConfigCategory category : group.getYACL().categories()) {
//                    categoryElement.addSubCategory(new CategoryEntryWidget(category, categoryListX + 10, y, categoryListWidth, widgetHeight));
//                    y += widgetHeight + 5;
//                }
//                categories.add(categoryElement);
//                addSelectableChild(categoryElement);
//            }
//        }
//
//        // Sort categories by name
//        categories.sort(Comparator.comparing(a -> a.getMessage().getString()));
//
//        redrawCategories(true);
//    }
//
//    public void redrawCategories(boolean skipRemoval) {
//        if(!skipRemoval) {
//            categories.forEach(element -> {
//                ArrayList<CategoryEntryWidget> elements = new ArrayList<>();
//                element.processSubcategories(elements::add);
//
//                for (CategoryEntryWidget category : elements) {
//                   remove(category);
//                }
//            });
//        }
//
//        // Reset scrollOffset if calculated height is less than the scissor region.
//        AtomicInteger maxY = new AtomicInteger(10);
//        for (CategoryEntryWidget category : categories) {
//            maxY.addAndGet(category.getHeight() + 5);
//            category.processSubcategories(subCategory -> maxY.addAndGet(subCategory.getHeight() + 5));
//        }
//
//        if(maxY.get() < height - 10) {
//            scrollOffsetY = 0;
//        }
//
//        int x = categoryListX, y = categoryListY;
//        for (CategoryEntryWidget category : categories) {
//           category.setX(x);
//           category.setY((int) (y+scrollOffsetY));
//           y += category.getHeight() + 5;
//           ArrayList<CategoryEntryWidget> subCategories = new ArrayList<>();
//           category.processSubcategories(subCategories::add);
//           for (CategoryEntryWidget subCategory : subCategories) {
//                subCategory.setX(x);
//                subCategory.setY((int) (y+scrollOffsetY));
//                y += subCategory.getHeight() + 5;
//                addSelectableChild(subCategory);
//           }
//        }
//    }
//
//    @Override
//    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
//        super.render(context, mouseX, mouseY, delta);
//
//        context.enableScissor(categoryListX, categoryListY, categoryListWidth+categoryListY, height - 10);
//
//        // Draw container for scissor region using fill and border.
//        ArrayList<CategoryEntryWidget> toRender = new ArrayList<>();
//        AtomicInteger maxY = new AtomicInteger(10);
//        for (CategoryEntryWidget category : this.categories) {
//            toRender.add(category);
//            maxY.addAndGet(category.getHeight() + 5);
//            category.processSubcategories(subCategory -> {
//                toRender.add(subCategory);
//                maxY.addAndGet(subCategory.getHeight() + 5);
//            });
//        }
//
//        context.drawBorder(10, 10, 120-10, height - 20, 0x70000000);
//        context.fill(10, 10, 120, height - 10, 0x10000000);
//
//        toRender.forEach(element -> element.render(context, mouseX, mouseY, delta));
//
//        // If the scroll offset is not 0, draw a shadow gradient at the top of the scissor region. to visualize the scroll.
//        if(scrollOffsetY != 0) {
//            // x1, y1, x2, y2, color1, color2
//            context.fillGradient(10, 0, 120, 30, 0xFF000000, 0x00000000);
//        }
//
//        context.fillGradient(10, height - 30, 120, height, 0x00000000, 0xFF000000);
//        context.disableScissor();
//
//        // Draw screen title above the scissor region.
//        context.drawCenteredTextWithShadow(textRenderer, title, categoryListX + (categoryListWidth / 2), (int) (categoryListY - (textRenderer.fontHeight * 1.5)), 0xFFFFFF);
//    }
//
//    public class CategoryEntryWidget extends ClickableWidget {
//        private @Nullable ConfigCategory category;
//        public boolean expanded = false;
//        private final ArrayList<CategoryEntryWidget> subCategories = new ArrayList<>();
//
//        public void addSubCategory(CategoryEntryWidget subCategory) {
//            subCategories.add(subCategory);
//        }
//
//        public void processSubcategories(Consumer<CategoryEntryWidget> consumer) {
//            if(!expanded) return;
//            for (CategoryEntryWidget subCategory : subCategories) {
//                consumer.accept(subCategory);
//            }
//        }
//
//        public CategoryEntryWidget(Text text, int x, int y, int width, int height) {
//            super(x, y, width, height, text);
//            this.category = null;
//        }
//
//        public CategoryEntryWidget(ConfigCategory category, int x, int y, int width, int height) {
//            super(x, y, width, height, category.name());
//            this.category = category;
//        }
//
//        @Override
//        protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
//            // Draw outline if focused/hovered
//            if(isHovered() || isFocused()) {
//                context.drawBorder(getX(), getY(), width, height, 0xFFFFFFFF);
//            }
//
//            // Draw box background
//            int backColour = category != null ? 0x80000000 : 0xFF000000;
//            context.fill(getX(), getY(), getX() + width, getY() + height, backColour);
//            if(category == null) {
//                // Draw expand symbol but larger than normal.
//                String expandSymbol = expanded ? "⌄" : "⌃";
//                float scale = 2f;
//
//                context.getMatrices().push();
//                context.getMatrices().scale(scale, scale, 1f);
//                int offset = expanded ? -2 : 4;
//                context.drawTextWithShadow(TestScreen.this.textRenderer, expandSymbol, (int) ((getX() + 6) / scale), (int) ((getY() + offset) / scale), 0xFFFFFF);
//                context.getMatrices().pop();
//               drawScrollableText(context, TestScreen.this.textRenderer, getMessage(), getX(), getY(), getX() + width - 5, getY() + height, 0xFFFFFF);
//            } else {
//                drawScrollableText(context, TestScreen.this.textRenderer, getMessage(), getX() + 5, getY(), getX() + width - 5, getY() + height, 0xFFFFFF);
//            }
//        }
//
//        @Override
//        public void onClick(double mouseX, double mouseY) {
//            if(category == null) {
//                expanded = !expanded;
//                redrawCategories(false);
//            }
//        }
//
//        @Override
//        protected void appendClickableNarrations(NarrationMessageBuilder builder) {}
//    }
//
//    public static float lerp(float delta, float start, float end) {
//        return (1 - delta) * start + delta * end;
//    }
//}
