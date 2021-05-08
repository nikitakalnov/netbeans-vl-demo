package view.diagram.actions.popup;

import org.netbeans.api.visual.action.PopupMenuProvider;
import org.netbeans.api.visual.widget.Widget;
import view.diagram.elements.Role;
import view.diagram.graph.Graph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WidgetPopupMenuProvider implements PopupMenuProvider {
  private final Widget widget;

  private final JPopupMenu menu = new JPopupMenu();
  private final JMenuItem deleteWidgetItem = new JMenuItem("Remove widget");

  public WidgetPopupMenuProvider(Widget widget) {
    this.widget = widget;

    addItem(deleteWidgetItem, deleteWidget);
  }

  @Override
  public JPopupMenu getPopupMenu(Widget widget, Point point) {
    return menu;
  }

  ActionListener deleteWidget = new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
      widget.removeFromParent();

      Graph ormGraph = (Graph)widget.getScene();
      ormGraph.getConnections()
              .forEach(c -> {
                if(c.getSourceAnchor().getRelatedWidget().equals(widget) || c.getTargetAnchor().getRelatedWidget().equals(widget))
                  c.removeFromParent();
              });
    }
  };

  protected void addItem(JMenuItem item, ActionListener listener) {
    item.addActionListener(listener);

    menu.add(item);
  }
}
